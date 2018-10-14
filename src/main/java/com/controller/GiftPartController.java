package com.controller;

import com.dto.GiftPartDto;
import com.service.GiftPartService;
import org.keycloak.KeycloakPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/giftPart")
public class GiftPartController {

    private final GiftPartService service;

    @Autowired
    public GiftPartController(GiftPartService service) {
        this.service = service;
    }

    @GetMapping("/{group}")
    public List<GiftPartDto> getAll(@PathVariable String group, KeycloakPrincipal principal) {
        return service.getAll(group, principal);
    }

    @PostMapping("/{groupName}")
    public void update(@RequestBody List<GiftPartDto> dtos, @PathVariable String groupName,
                       KeycloakPrincipal principal) {
        service.update(dtos, groupName, principal);
    }

}
