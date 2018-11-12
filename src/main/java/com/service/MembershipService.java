package com.service;

import com.algorithm.DrawForwardChecking;
import com.algorithm.Options;
import com.algorithm.Result;
import com.dao.GroupDao;
import com.dao.MembershipDao;
import com.dao.UserDao;
import com.domain.Group;
import com.domain.Membership;
import com.domain.User;
import com.dto.MemberGroupDto;
import com.dto.UserDto;
import com.dto.UserIncludeDto;
import com.mapper.UserMapper;
import com.service.mail.MailException;
import com.service.mail.MailService;
import com.util.GlobalLogger;
import com.util.PrincipalUtil;
import javafx.util.Pair;
import org.keycloak.KeycloakPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MembershipService {

    private final UserDao userDao;
    private final GroupDao groupDao;
    private final MembershipDao membershipDao;
    private final MailService mailService;

    @Autowired
    public MembershipService(UserDao userDao, GroupDao groupDao, MembershipDao membershipDao, MailService mailService) {
        this.userDao = userDao;
        this.groupDao = groupDao;
        this.membershipDao = membershipDao;
        this.mailService = mailService;
    }

    public List<UserDto> getGroupRequests(String name) {
        Group group = groupDao.findByName(name);
        if (group == null) {
            throw new IllegalArgumentException("Group " + name + "doesn't exist");
        }
        return group.getMemberships()
                .stream()
                .filter(mem -> mem.getAccepted() == null)
                .map(mem -> UserMapper.toDto(mem.getUser()))
                .collect(Collectors.toList());
    }

    public void setAccepted(String groupName, String username, String value) {
        Group group = groupDao.findByName(groupName);
        Membership membership = group.getMemberships()
                .stream()
                .filter(mem -> mem.getUser().getPreferredUsername().equals(username))
                .collect(Collectors.toList())
                .get(0);
        membership.setAccepted(Boolean.valueOf(value));
        if (Boolean.valueOf(value)) {
            membership.setIncludedInLastDraw(false);
        }
        membershipDao.save(membership);
    }

    public Set<UserIncludeDto> getGroupMembers(String groupName) {
        Group group = groupDao.findByName(groupName);
        if (group == null) {
            throw new IllegalArgumentException("Group " + groupName + "doesn't exist");
        }
        return group.getMemberships()
                .stream()
                .filter(mem -> mem.getAccepted() != null && mem.getAccepted())
                .map(mem -> UserMapper.toIncludeDto(mem.getUser(), mem.isIncludeInFutureDraw(), mem.getIncludedInLastDraw()))
                .collect(Collectors.toSet());
    }

    @Transactional
    public void performDraw(String groupName) {
        System.out.println("DRAW   @@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        Group group = groupDao.findByName(groupName);
        group.getMemberships().stream()
                .filter(m -> !m.isIncludeInFutureDraw())
                .forEach(m -> {
                    m.setDrawnUser(null);
                    m.setIncludedInLastDraw(false);
                    membershipDao.save(m);
                });
        List<Membership> memberships = group.getMemberships().stream()
                .filter(Membership::isIncludeInFutureDraw)
                .collect(Collectors.toList());
        if (memberships.size() < 3) throw new IllegalArgumentException(
                "The number of users included in draw should be greater than 3.");
        Random random = new Random(System.currentTimeMillis());
        Result algorithmResult = new DrawForwardChecking(memberships.size(), Options.getDefaults()).run();
        List<List<Integer>> foundSolutions = algorithmResult.foundSolutions;
        List<Integer> permutation = foundSolutions.get(random.nextInt(foundSolutions.size()));
        GlobalLogger.info("" + permutation);
        for (int i = 0; i < memberships.size(); i++) {
            Membership membership = memberships.get(i);
            User drawnUser = memberships.get(permutation.get(i) - 1).getUser();
            membership.setDrawnUser(drawnUser);
            membership.setIncludedInLastDraw(true);
            membershipDao.save(membership);
            System.out.println("i: " + i + " perm -1: " + (permutation.get(i) - 1));
        }
        BigDecimal v = calculateChildGiftValue(memberships, group);
        if (group.isCountChildren()) {
            group.setCalculatedChildGiftValue(v);
        }
        group.setDrawn(true);
        group.setMailsSent(false);
        groupDao.save(group);
    }

    private void sendMails(List<Membership> memberships) {
        mailService.send(
                memberships.stream()
                        .filter(m -> m.getAccepted() != null & m.getAccepted() && m.isIncludeInFutureDraw() && !m.getUser().getVirtual())
                        .collect(Collectors.toList()));
    }

    private BigDecimal calculateChildGiftValue(List<Membership> memberships, Group group) {
        int allChildrenCount = memberships.stream().map(Membership::getUser).mapToInt(User::getChildren).sum();
        int membershipsCount = memberships.stream().filter(m -> m.isIncludeInFutureDraw() && (m.getAccepted() != null && m.getAccepted()))
                .collect(Collectors.toList()).size();
        return new BigDecimal(allChildrenCount)
                .multiply(group.getChildGiftValue())
                .divide(new BigDecimal(membershipsCount), RoundingMode.HALF_UP);
    }

    public UserDto getDrawUser(String groupName, KeycloakPrincipal principal) {
        User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
        List<Membership> selectedGroupMemberships = user.getMembershipsWhereUser()
                .stream()
                .filter(mem -> mem.getGroup().getName().equals(groupName))
                .collect(Collectors.toList());

        if (selectedGroupMemberships.isEmpty()) {
            throw new IllegalArgumentException(
                    "User " + user.getPreferredUsername() + " is not a member of group " + groupName);
        }
        Membership membership = selectedGroupMemberships.get(0);
        User drawnUser = membership.getDrawnUser();
        return drawnUser == null ? null : UserMapper.toDto(drawnUser);

    }

    public UserDto getDrawUser(String groupName, String username) {
        User user = userDao.findByPreferredUsername(username);
        List<Membership> selectedGroupMemberships = user.getMembershipsWhereUser()
                .stream()
                .filter(mem -> mem.getGroup().getName().equals(groupName))
                .collect(Collectors.toList());

        if (selectedGroupMemberships.isEmpty()) {
            throw new IllegalArgumentException(
                    "User " + user.getPreferredUsername() + " is not a member of group " + groupName);
        }
        Membership membership = selectedGroupMemberships.get(0);
        User drawnUser = membership.getDrawnUser();
        return drawnUser == null ? null : UserMapper.toDto(drawnUser);

    }

    public List<String> getUserOwnerGroups(KeycloakPrincipal principal) {
        User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
        List<Membership> memberships = membershipDao.findByUserIdAndOwns(user.getId(), true);
        return memberships.stream()
                .map(m -> m.getGroup().getName())
                .collect(Collectors.toList());
    }

    public List<MemberGroupDto> getUserMemberGroups(KeycloakPrincipal principal) {
        User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
        List<Membership> memberships = membershipDao.findByUserIdAndOwns(user.getId(), false);
        return memberships.stream()
                .map(m -> new MemberGroupDto(m.getGroup().getName(), m.getAccepted(), m.getGroup().isDrawn()))
                .collect(Collectors.toList());
    }

    public void addRequest(String groupName, KeycloakPrincipal principal) {
        Group storedGroup = groupDao.findByName(groupName);
        if (storedGroup != null) {
            User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
            membershipDao.save(new Membership(null, false, null, true,
                    null, storedGroup, user, null, null));
        }
    }

    @Transactional
    public void updateIncludeMembers(List<UserIncludeDto> userIncludeDtos, String groupName) { //TODO by group id
        Map<String, Pair<Boolean, Boolean>> includesMap = new HashMap<>();
        userIncludeDtos.forEach(dto -> includesMap.put(
                dto.getPreferredUsername(),
                new Pair<>(dto.isIncludeInFutureDraw(), dto.getIncludedInLastDraw())
        ));
        Long groupId = groupDao.findByName(groupName).getId();
        List<Membership> memberships = membershipDao.findByGroupId(groupId);
        for (Membership mem : memberships) {
            Pair<Boolean, Boolean> includes = includesMap.get(mem.getUser().getPreferredUsername());
            if (includes != null) {
                mem.setIncludeInFutureDraw(includes.getKey());
                mem.setIncludedInLastDraw(includes.getValue());
            }
        }
        membershipDao.save(memberships);
        System.out.println("INCLUDES UPDATED   @@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

    @Transactional
    public void deleteMembership(String groupName, KeycloakPrincipal principal) {
        User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
        Group group = groupDao.findByName(groupName);
        membershipDao.deleteByUserIdAndGroupId(user.getId(), group.getId());
    }

    public void addByHand(String groupName, UserDto userDto) {
        Group storedGroup = groupDao.findByName(groupName);
        if (storedGroup != null) { //todo else throw sth
            Long maxId = userDao.findTopByOrderByIdDesc().getId();
            User user = userDao.save(UserMapper.toUser(userDto, maxId));
            membershipDao.save(new Membership(null, false, true, true,
                    false, storedGroup, user, null, null));
        }
    }

    public void sendMails(String groupName) {
        Group group = groupDao.findByName(groupName);
        List<Membership> memberships = group.getMemberships().stream()
                .filter(m -> m.getIncludedInLastDraw() != null && m.getIncludedInLastDraw())
                .collect(Collectors.toList());
        try {
            sendMails(memberships);
            group.setMailsSent(true);
            groupDao.save(group);
        } catch (Exception e) {
            throw new MailException(e.getMessage(), e.getCause());
        }
    }

}
