package com.controller;

import com.dao.GroupDao;
import com.dao.MembershipDao;
import com.dao.UserDao;
import com.domain.Group;
import com.domain.Membership;
import com.domain.User;
import com.dto.MemberGroupDto;
import com.dto.UserDto;
import com.mapper.UserMapper;
import com.util.GlobalLogger;
import com.util.PrincipalUtil;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserDao userDao;
    private GroupDao groupDao;
    private MembershipDao membershipDao;

    @Autowired
    public UserController(UserDao userDao, GroupDao groupDao, MembershipDao membershipDao) {
        this.userDao = userDao;
        this.groupDao = groupDao;
        this.membershipDao = membershipDao;
    }

    @GetMapping(path = "/{preferredUsername}")
    public UserDto getUser(@PathVariable String preferredUsername, KeycloakPrincipal principal) {
        GlobalLogger.info(preferredUsername);
        User user = userDao.findByPreferredUsername(preferredUsername);
        GlobalLogger.info("user: " + user);
        return user == null ? saveUser(principal) : UserMapper.toDto(user);
    }

    private UserDto saveUser(KeycloakPrincipal principal) {
        AccessToken token = principal.getKeycloakSecurityContext().getToken();
        userDao.save(new User(
                null,
                token.getPreferredUsername(),
                token.getGivenName(),
                token.getFamilyName(),
                null,
                0,
                null,
                null
        ));
        return UserMapper.toDto(userDao.findByPreferredUsername(token.getPreferredUsername()));
    }//TODO move to service


    @GetMapping(path = "/draw/{groupName}")
    public UserDto getDrawUser(@PathVariable String groupName, KeycloakPrincipal principal) {
        User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
        List<Membership> selectedGroupMemberships = user.getMembershipsWhereUser()
                .stream()
                .filter(mem -> mem.getGroup().getName().equals(groupName))
                .collect(Collectors.toList());

        if (!selectedGroupMemberships.isEmpty()) {
            Membership membership = selectedGroupMemberships.get(0);
            User drawnUser = membership.getDrawnUser();
            return drawnUser == null ? null : UserMapper.toDto(drawnUser);
        } else {
            throw new IllegalArgumentException(
                    "User " + user.getPreferredUsername() + " is not a member of group " + groupName);
        }
    }

    @RequestMapping(path = "/edit", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void editUser(@RequestBody UserDto userDto) {
        User user = userDao.findByPreferredUsername(userDto.getPreferredUsername());
        user.setAbout(userDto.getAbout());
        user.setChildren(userDto.getChildren());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        userDao.save(user);
    }

    @RequestMapping(path = "/create/{groupName}", method = RequestMethod.POST)
    private Boolean createGroup(@PathVariable String groupName, KeycloakPrincipal principal) {
        Group storedGroup = groupDao.findByName(groupName);
        if (storedGroup == null) {
            Group createdGroup = groupDao.save(new Group(groupName));
            User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
            membershipDao.save(new Membership(null, true, true,
                    false, true,
                    user.getAbout(), user.getChildren(), createdGroup,
                    user, null, null));
            return true;
        } else {
            return false;
        }
    }

    @GetMapping(path = "/groups/member")
    public List<MemberGroupDto> getUserMemberGroups(KeycloakPrincipal principal) {
        return getUserMemberGroupDtos(principal);
    }

    @GetMapping(path = "/groups/owner")
    public List<String> getUserOwnerGroups(KeycloakPrincipal principal) {
        return getUserGroupsWhereOwner(principal);
    }

    private List<String> getUserGroupsWhereOwner(KeycloakPrincipal principal) {
        User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
        List<Membership> memberships = membershipDao.findByUserIdAndOwns(user.getId(), true);
        return memberships.stream()
                .map(m -> m.getGroup().getName())
                .collect(Collectors.toList());
    }

    private List<MemberGroupDto> getUserMemberGroupDtos(KeycloakPrincipal principal) {
        User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
        List<Membership> memberships = membershipDao.findByUserIdAndOwns(user.getId(), false);
        return memberships.stream()
                .map(m -> new MemberGroupDto(m.getGroup().getName(), m.isAccepted()))
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/requestGroup/{groupName}", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    private void requestGroup(@PathVariable String groupName, KeycloakPrincipal principal) {
        Group storedGroup = groupDao.findByName(groupName);
        if (storedGroup != null) {
            User user = PrincipalUtil.getUserByPrincipal(principal, userDao);
            membershipDao.save(new Membership(null, false, false,
                    false, true,
                    user.getAbout(), user.getChildren(), storedGroup,
                    user, null, null));
        }
    }

}
