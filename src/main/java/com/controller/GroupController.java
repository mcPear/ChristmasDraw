package com.controller;

import com.dao.GroupDao;
import com.dao.MembershipDao;
import com.dao.UserDao;
import com.domain.Group;
import com.domain.Membership;
import com.domain.User;
import com.dto.GroupDto;
import com.dto.GroupSimpleDto;
import com.dto.UserDto;
import com.mapper.GroupMapper;
import com.mapper.UserMapper;
import com.util.PrincipalUtil;
import org.keycloak.KeycloakPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    private UserDao userDao;
    private GroupDao groupDao;
    private MembershipDao membershipDao;

    @Autowired
    public GroupController(UserDao userDao, GroupDao groupDao, MembershipDao membershipDao) {
        this.userDao = userDao;
        this.groupDao = groupDao;
        this.membershipDao = membershipDao;
    }

    @RequestMapping(path = "/exists/{groupName}", method = RequestMethod.GET)
    private Boolean groupExists(@PathVariable String groupName) {
        return groupDao.findByName(groupName) != null;
    }

    @GetMapping(path = "/{groupName}")
    public GroupDto getDrawData(@PathVariable String groupName, KeycloakPrincipal principal) {
        User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
        List<Membership> selectedGroupMemberships = user.getMembershipsWhereUser()
                .stream()
                .filter(mem -> mem.getGroup().getName().equals(groupName))
                .collect(Collectors.toList());

        if (!selectedGroupMemberships.isEmpty()) {
            Membership membership = selectedGroupMemberships.get(0);
            Group group = membership.getGroup();
            return GroupMapper.toDto(group);
        } else {
            throw new IllegalArgumentException(
                    "User " + user.getPreferredUsername() + " is not a member of group " + groupName);
        }
    }

    @GetMapping(path = "/requests/{groupName}")
    public List<UserDto> getRequests(@PathVariable String groupName) {
        Group group = groupDao.findByName(groupName);
        if (group != null) {
            return group.getMemberships()
                    .stream()
                    .filter(mem -> mem.getAccepted() == null)
                    .map(mem -> UserMapper.toDto(mem.getUser()))
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Group " + groupName + "doesn't exist");
        }
    }

    @RequestMapping(path = "/accept/{groupName}/{username}/{value}", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    private void setAcceptedRequest(@PathVariable String groupName, @PathVariable String username,
                                    @PathVariable String value) {
        Group group = groupDao.findByName(groupName);
        Membership membership = group.getMemberships()
                .stream()
                .filter(mem -> mem.getUser().getPreferredUsername().equals(username))
                .collect(Collectors.toList())
                .get(0);
        membership.setAccepted(Boolean.valueOf(value));
        System.out.println(Boolean.valueOf(value));
        membershipDao.save(membership);
    }

    @GetMapping(path = "/members/{groupName}")
    public Set<UserDto> getMembers(@PathVariable String groupName, KeycloakPrincipal principal) {
        Group group = groupDao.findByName(groupName);
        if (group != null) {
            return group.getMemberships()
                    .stream()
                    .filter(mem -> mem.getAccepted() != null && mem.getAccepted())
                    .map(mem -> UserMapper.toDto(mem.getUser()))
                    .collect(Collectors.toSet());
        } else {
            throw new IllegalArgumentException("Group " + groupName + "doesn't exist");
        }
    }

    @RequestMapping(path = "/draw/{groupName}", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    private void performDraw(@PathVariable String groupName) {
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


    @GetMapping(path = "/getAll/{username}")
    public List<GroupSimpleDto> getGroups(@PathVariable String username, KeycloakPrincipal principal) {
        User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
        if (user != null) {
            List<Group> list = groupDao.findAll();
            List<GroupSimpleDto> dtoList = new ArrayList<>();
            for (Group item : list) {
                dtoList.add(new GroupSimpleDto(item.getId(), item.getName(), item.getDrawDate()));
            }
            return dtoList;
        } else {
            throw new IllegalArgumentException("Something is not quite right");
        }
    }

    @DeleteMapping(path = "/delete/{groupName}/{username}")
    public void deleteGroup(@PathVariable String groupName, @PathVariable String username, KeycloakPrincipal principal) {
        User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
        if (user != null) {
            Group group = groupDao.findByName(groupName);
            groupDao.delete(group.getId());
        } else {
            throw new IllegalArgumentException("Something is not quite right");
        }
    }
//    getRequests(groupName: string): Promise<UserDto[]> {
//        return this.http.get<UserDto[]>('http://localhost:8090/api/group/requests/' + name).toPromise();
//    }
//
//    acceptRequest(username: string, groupName: string): Promise<object> {
//        return this.http.post(
//                'http://localhost:8090/api/group/accept/' + groupName + '/' + username, {}).toPromise();
//    }

}
