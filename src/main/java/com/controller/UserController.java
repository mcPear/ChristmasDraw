package com.controller;

import com.dto.UserDto;
import com.service.UserService;
import org.keycloak.KeycloakPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{preferredUsername}")
    public UserDto getUser(@PathVariable String preferredUsername, KeycloakPrincipal principal) {
        return userService.getOneDto(preferredUsername, principal);
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void update(@RequestBody UserDto userDto) {
        userService.update(userDto);
    }

}
