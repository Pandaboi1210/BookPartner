package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.entity.Roysched;
import com.sprint.BookPartnerApplication.services.RoyschedService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/royalties")
public class RoyschedController {

    private final RoyschedService royschedService;

    public RoyschedController(RoyschedService royschedService) {
        this.royschedService = royschedService;
    }

    // POST /api/v1/royalties - Create royalty slab
    @PostMapping
    public Roysched createRoysched(@RequestBody Roysched roysched) {
        return royschedService.createRoysched(roysched);
    }

    // GET /api/v1/royalties/title/{titleId} - Get royalty slabs for a title
    @GetMapping("/title/{titleId}")
    public List<Roysched> getRoyschedByTitle(@PathVariable String titleId) {
        return royschedService.getRoyschedByTitle(titleId);
    }

    // PUT /api/v1/royalties/{royaltyId} - Update royalty percentage
    @PutMapping("/{royaltyId}")
    public Roysched updateRoysched(@PathVariable Integer royaltyId, @RequestBody Roysched roysched) {
        return royschedService.updateRoysched(royaltyId, roysched);
    }
}