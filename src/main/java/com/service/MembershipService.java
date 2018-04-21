package com.service;

import com.dao.GroupDao;
import com.dao.MembershipDao;
import com.dao.UserDao;
import com.domain.Group;
import com.domain.Membership;
import com.domain.User;
import com.dto.MemberGroupDto;
import com.dto.UserDto;
import com.mapper.UserMapper;
import com.util.PrincipalUtil;
import org.keycloak.KeycloakPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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

    public Set<UserDto> getGroupMembers(String groupName) {
        Group group = groupDao.findByName(groupName);
        if (group == null) {
            throw new IllegalArgumentException("Group " + groupName + "doesn't exist");
        }
        return group.getMemberships()
                .stream()
                .filter(mem -> mem.getAccepted() != null && mem.getAccepted())
                .map(mem -> UserMapper.toDto(mem.getUser()))
                .collect(Collectors.toSet());
    }

    public void performDraw(String groupName) { //FIXME forward-checking ?
        Group group = groupDao.findByName(groupName);
        Set<Membership> memberships = group.getMemberships();
        if (memberships.size() < 2) throw new IllegalArgumentException("Group size should be greater than 2.");
        List<User> users = memberships.stream().map(m -> m.getUser()).collect(Collectors.toList());
        memberships.forEach(m -> {
            int i = 0;
            User chosenUser = users.get(i);
            while (chosenUser.equals(m.getUser())) {
                chosenUser = users.get(++i);
            }
            m.setDrawnUser(chosenUser);
            membershipDao.save(m);
        });
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

    //TODO missing unaccepted (null) memberships
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

}
