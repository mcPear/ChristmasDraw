package com.controller;

import com.dao.GroupDao;
import com.dao.MembershipDao;
import com.dao.UserDao;
import com.domain.Group;
import com.domain.Membership;
import com.domain.User;
import com.dto.UserDto;
import com.mapper.UserMapper;
import com.util.GlobalLogger;
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
            User user = getUserByPrincipal(principal);
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
    public List<String> getUserMemberGroups(KeycloakPrincipal principal) {
        return getUserGroups(false, principal);
    }

    @GetMapping(path = "/groups/owner")
    public List<String> getUserOwnerGroups(KeycloakPrincipal principal) {
        return getUserGroups(true, principal);
    }

    private List<String> getUserGroups(boolean isOwner, KeycloakPrincipal principal) {
        User user = getUserByPrincipal(principal);
        List<Membership> memberships = membershipDao.findByUserIdAndOwns(user.getId(), isOwner);
        return memberships.stream()
                .map(m -> m.getGroup().getName())
                .collect(Collectors.toList());
    }

    private String getPreferredUsername(KeycloakPrincipal principal) {
        AccessToken token = principal.getKeycloakSecurityContext().getToken();
        return token.getPreferredUsername();
    }

    private User getUserByPrincipal(KeycloakPrincipal principal) {
        String preferredUsername = getPreferredUsername(principal);
        return userDao.findByPreferredUsername(preferredUsername);
    }

    @RequestMapping(path = "/requestGroup/{groupName}", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.CREATED)
    private void requestGroup(@PathVariable String groupName, KeycloakPrincipal principal) {
        Group storedGroup = groupDao.findByName(groupName);
        if (storedGroup != null) {
            User user = getUserByPrincipal(principal);
            membershipDao.save(new Membership(null, false, false,
                    false, true,
                    user.getAbout(), user.getChildren(), storedGroup,
                    user, null, null));
        }
    }

}
