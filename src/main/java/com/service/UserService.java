package com.service;

import com.dao.UserDao;
import com.domain.User;
import com.dto.UserDto;
import com.mapper.UserMapper;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDto getOneDto(String preferredUsername, KeycloakPrincipal principal) {
        User user = userDao.findByPreferredUsername(preferredUsername);
        return user == null ? save(principal) : UserMapper.toDto(user);
    }

    private UserDto save(KeycloakPrincipal principal) {
        AccessToken token = principal.getKeycloakSecurityContext().getToken();
        User user = UserMapper.toUser(token);
        userDao.save(user);
        return UserMapper.toDto(user);
    }

    public void update(UserDto userDto) {
        User user = userDao.findOne(userDto.getId());
        user.setAbout(userDto.getAbout());
        user.setChildren(userDto.getChildren());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        userDao.save(user);
    }

}
