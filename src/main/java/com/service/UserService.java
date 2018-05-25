package com.service;

import com.dao.UserDao;
import com.domain.User;
import com.dto.UserDto;
import com.mapper.UserMapper;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        if(token != null) {
            User user = UserMapper.toUser(token);
            userDao.save(user);
            return UserMapper.toDto(user);
        }else return null;
    }

    public void update(UserDto userDto) {
        User user = userDao.findOne(userDto.getId());
        user.setAbout(userDto.getAbout());
        user.setChildren(userDto.getChildren());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        userDao.save(user);
    }

    public List<UserDto> getAll(){
        List<User> users = userDao.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user: users) {
            userDtos.add(UserMapper.toDto(user));
        }
        return  userDtos;
    }

    public void delete(String username, KeycloakPrincipal principal){
        AccessToken token = principal.getKeycloakSecurityContext().getToken();
        User user = UserMapper.toUser(token);
        UserDto userDto = getOneDto(username, principal);
        if(user.getId() != userDto.getId())
            userDao.delete(userDto.getId());
    }

}
