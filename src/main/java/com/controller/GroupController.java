package com.controller;

import com.dto.GroupDto;
import com.dto.GroupSimpleDto;
import com.service.GroupService;
import org.keycloak.KeycloakPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @RequestMapping(path = "/exists/{name}", method = RequestMethod.GET)
    private Boolean existsGroup(@PathVariable String name) {
        return groupService.existsGroup(name);
    }

    @GetMapping(path = "/{name}")
    public GroupDto getOne(@PathVariable String name) {
        return groupService.getOneDto(name);
    }

    @GetMapping(path = "/getAll")
    public List<GroupSimpleDto> getAll(KeycloakPrincipal principal) {
        return groupService.getAll(principal);
    }

    @DeleteMapping(path = "/{groupName}")
    public void delete(@PathVariable String groupName, KeycloakPrincipal principal) {
        groupService.delete(groupName, principal);
    }

    @RequestMapping(path = "/create/{name}", method = RequestMethod.POST)
    private Boolean add(@PathVariable String name, KeycloakPrincipal principal) {
        return groupService.add(name, principal);
    }

}
