package com.controller;

import com.dto.GiftPartDto;
import com.service.GiftPartService;
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

    @GetMapping("/{membershipId}")
    public List<GiftPartDto> getAll(@PathVariable Long membershipId) {
        return service.getAll(membershipId);
    }

    @PostMapping("/add/{membershipId}")
    public void add(@RequestBody GiftPartDto dto, @PathVariable Long membershipId) {
        service.add(dto, membershipId);
    }

    @PostMapping("/update")
    public void update(@RequestBody GiftPartDto dto) {
        service.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
