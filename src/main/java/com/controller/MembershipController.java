package com.controller;

import com.dto.MemberGroupDto;
import com.dto.UserDto;
import com.dto.UserIncludeDto;
import com.service.MembershipService;
import org.keycloak.KeycloakPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/membership")
public class MembershipController {

    private MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @GetMapping(path = "/requests/{name}")
    public List<UserDto> getGroupRequests(@PathVariable String name) {
        return membershipService.getGroupRequests(name);
    }

    @RequestMapping(path = "/accept/{groupName}/{username}/{value}", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    private void setAccepted(@PathVariable String groupName, @PathVariable String username,
                             @PathVariable String value) {
        membershipService.setAccepted(groupName, username, value);
    }

    @GetMapping(path = "/members/{name}")
    public Set<UserIncludeDto> getGroupMembers(@PathVariable String name) {
        return membershipService.getGroupMembers(name);
    }

    @RequestMapping(path = "/draw/{groupName}", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    private void performDraw(@PathVariable String groupName) {
        membershipService.performDraw(groupName);
    }

    @GetMapping(path = "/drawUser/{groupName}") //FIXME use username/id
    public UserDto getDrawUser(@PathVariable String groupName, KeycloakPrincipal principal) {
        return membershipService.getDrawUser(groupName, principal);
    }

    @GetMapping(path = "/groups/member")
    public List<MemberGroupDto> getUserMemberGroups(KeycloakPrincipal principal) { //FIXME use username/id
        return membershipService.getUserMemberGroups(principal);
    }

    @GetMapping(path = "/groups/owner")
    public List<String> getUserOwnerGroups(KeycloakPrincipal principal) { //FIXME use username/id
        return membershipService.getUserOwnerGroups(principal);
    }

    @RequestMapping(path = "/requestGroup/{groupName}", method = RequestMethod.POST) //FIXME use username/id
    @ResponseStatus(code = HttpStatus.CREATED)
    private void requestGroup(@PathVariable String groupName, KeycloakPrincipal principal) {
        membershipService.addRequest(groupName, principal);
    }

    @RequestMapping(path = "/updateIncludeMembers/{groupName}", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    private void updateIncludeMembers(@PathVariable String groupName, @RequestBody List<UserIncludeDto> userIncludeDtos) {
        membershipService.updateIncludeMembers(userIncludeDtos, groupName);
    }

    @RequestMapping(path = "/{groupName}", method = RequestMethod.DELETE)
    @ResponseStatus(code = HttpStatus.OK)
    private void deleteMembership(@PathVariable String groupName, KeycloakPrincipal principal) {
        membershipService.deleteMembership(groupName, principal);
    }

}
