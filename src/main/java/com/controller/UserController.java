package com.controller;

import com.dto.UserDto;
import com.service.UserService;
import org.keycloak.KeycloakPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{preferredUsername}")
    public UserDto getUser(@PathVariable String preferredUsername, KeycloakPrincipal principal, HttpServletRequest request) {
        return userService.getOneDto(preferredUsername, principal, request);
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void update(@RequestBody UserDto userDto) {
        userService.update(userDto);
    }

    @GetMapping(path = "/getAll")
    public List<UserDto> getUsers() {
        return userService.getAll();
    }

    @DeleteMapping(path = "/{username}")
    public void delete(@PathVariable String username) {
        userService.delete(username);
    }

    @GetMapping(path = "/virtual/{username}")
    public Boolean isVirtual(@PathVariable String username) {
        return userService.isVirtual(username);
    }
}
