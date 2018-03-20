package com.controller;

import com.dao.UserDao;
import com.domain.User;
import com.util.GlobalLogger;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/fetch", method = RequestMethod.GET)
    public User fetchUser(KeycloakPrincipal principal) {
        AccessToken token = principal.getKeycloakSecurityContext().getToken();
        GlobalLogger.info("aaa: " + token.getNickName());
        User user = userDao.findByPreferredUsername(token.getPreferredUsername());
        if (user == null) {
            userDao.save(new User(
                    null,
                    token.getPreferredUsername(),
                    token.getGivenName(),
                    token.getFamilyName(),
                    null,
                    null,
                    null,
                    null
            ));
        }
        return userDao.findByPreferredUsername(token.getPreferredUsername());
    }

}
