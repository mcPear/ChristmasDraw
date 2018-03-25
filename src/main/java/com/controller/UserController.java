package com.controller;

import com.dao.UserDao;
import com.domain.User;
import com.dto.UserDto;
import com.mapper.UserMapper;
import com.util.GlobalLogger;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
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
}
