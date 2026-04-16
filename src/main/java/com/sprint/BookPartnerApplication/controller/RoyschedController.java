package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.entity.Roysched;
import com.sprint.BookPartnerApplication.servicesImpl.RoyschedServiceImpl;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roysched")
public class RoyschedController {

    private final RoyschedServiceImpl royschedService;

    public RoyschedController(RoyschedServiceImpl royschedService) {
        this.royschedService = royschedService;
    }

    @GetMapping
    public List<Roysched> getAllRoysched() {
        return royschedService.getAllRoysched();
    }

    @GetMapping("/{id}")
    public Optional<Roysched> getRoyschedById(@PathVariable Integer id) {
        return royschedService.getRoyschedById(id);
    }

    @PostMapping
    public Roysched createRoysched(@RequestBody Roysched roysched) {
        return royschedService.createRoysched(roysched);
    }

    @PutMapping("/{id}")
    public Roysched updateRoysched(
            @PathVariable Integer id,
            @RequestBody Roysched roysched) {
        return royschedService.updateRoysched(id, roysched);
    }

    @DeleteMapping("/{id}")
    public void deleteRoysched(@PathVariable Integer id) {
        royschedService.deleteRoysched(id);
    }
}