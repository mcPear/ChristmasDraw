package com.service;

import com.dao.UserDao;
import com.domain.User;
import com.dto.UserDto;
import com.mapper.UserMapper;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDto getOneDto(String preferredUsername, KeycloakPrincipal principal, HttpServletRequest request) {
        String lang = getLang(request);
        User user = userDao.findByPreferredUsername(preferredUsername);
        return user == null ? save(principal, lang) : UserMapper.toDto(user);
    }

    private UserDto save(KeycloakPrincipal principal, String lang) {
        AccessToken token = principal.getKeycloakSecurityContext().getToken();
        if (token != null) {
            User user = UserMapper.toUser(token, lang);
            userDao.save(user);
            return UserMapper.toDto(user);
        } else return null;
    }

    private String getLang(HttpServletRequest request) {
        return request.getHeader("Accept-Language").substring(0, 2);
    }

    public void update(UserDto userDto) {
        User user = userDao.findOne(userDto.getId());
        user.setAbout(userDto.getAbout());
        user.setChildren(userDto.getChildren());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        userDao.save(user);
    }

    public List<UserDto> getAll() {
        List<User> users = userDao.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(UserMapper.toDto(user));
        }
        return userDtos;
    }

    public void delete(String username) {
        User user = userDao.findByPreferredUsername(username);
        if (user != null) {
            userDao.delete(user);
        }
    }

    public Boolean isVirtual(String username) {
        return userDao.findByPreferredUsername(username).getVirtual();
    }

}
