package com.service;

import com.algorithm.DrawForwardCheck;
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
import com.util.GlobalLogger;
import com.util.PrincipalUtil;
import org.keycloak.KeycloakPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MembershipService {

    private UserDao userDao;
    private GroupDao groupDao;
    private MembershipDao membershipDao;

    @Autowired
    public MembershipService(UserDao userDao, GroupDao groupDao, MembershipDao membershipDao) {
        this.userDao = userDao;
        this.groupDao = groupDao;
        this.membershipDao = membershipDao;
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
                .map(mem -> UserMapper.toIncludeDto(mem.getUser(), mem.isIncludeInDraw()))
                .collect(Collectors.toSet());
    }

    public void performDraw(String groupName) {
        Group group = groupDao.findByName(groupName);
        List<Membership> memberships = group.getMemberships().stream()
                .filter(Membership::isIncludeInDraw)
                .collect(Collectors.toList());
        if (memberships.size() < 2) throw new IllegalArgumentException(
                "The number of users included in draw should be greater than 2.");
        Random random = new Random(System.currentTimeMillis());
        Result algorithmResult = new DrawForwardCheck(memberships.size(), Options.getDefaultInstance()).run();
        List<List<Integer>> foundSolutions = algorithmResult.foundSolutions;
        List<Integer> permutation = foundSolutions.get(random.nextInt(foundSolutions.size()));
        GlobalLogger.info("" + permutation);
        for (int i = 0; i < memberships.size(); i++) {
            Membership membership = memberships.get(i);
            User drawnUser = memberships.get(permutation.get(i) - 1).getUser();
            membership.setDrawnUser(drawnUser);
            membershipDao.save(membership);
            System.out.println("i: " + i + " perm -1: " + (permutation.get(i) - 1));
        }
        group.setDrawn(true);
        groupDao.save(group);
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
                .map(m -> new MemberGroupDto(m.getGroup().getName(), m.getAccepted()))
                .collect(Collectors.toList());
    }

    public void addRequest(String groupName, KeycloakPrincipal principal) {
        Group storedGroup = groupDao.findByName(groupName);
        if (storedGroup != null) {
            User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
            membershipDao.save(new Membership(null, false, null,
                    false, true,
                    user.getAbout(), user.getChildren(), storedGroup,
                    user, null, null));
        }
    }

    public void updateIncludeMembers(List<UserIncludeDto> userIncludeDtos, String groupName) { //TODO by group id
        Map<String, Boolean> includesMap = new HashMap<>();
        userIncludeDtos.forEach(dto -> includesMap.put(dto.getPreferredUsername(), dto.isInclude()));
        Long groupId = groupDao.findByName(groupName).getId();
        List<Membership> memberships = membershipDao.findByGroupId(groupId);
        for (Membership mem : memberships) {
            mem.setIncludeInDraw(includesMap.get(mem.getUser().getPreferredUsername()));
        }
        membershipDao.save(memberships);
    }

}
