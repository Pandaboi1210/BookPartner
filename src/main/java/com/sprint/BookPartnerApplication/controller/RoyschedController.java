package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.RoyschedRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.RoyschedResponseDTO;
import com.sprint.BookPartnerApplication.services.RoyschedService;

import jakarta.validation.Valid;
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
    public RoyschedResponseDTO createRoysched(@Valid @RequestBody RoyschedRequestDTO requestDTO) {
        return royschedService.createRoysched(requestDTO);
    }

    // GET /api/v1/royalties/title/{titleId} - Get royalty slabs for a title
    @GetMapping("/title/{titleId}")
    public List<RoyschedResponseDTO> getRoyschedByTitle(@PathVariable String titleId) {
        return royschedService.getRoyschedByTitle(titleId);
    }

    // PUT /api/v1/royalties/{royaltyId} - Update royalty percentage
    @PutMapping("/{royaltyId}")
    public RoyschedResponseDTO updateRoysched(@PathVariable Integer royaltyId,
                                              @Valid @RequestBody RoyschedRequestDTO requestDTO) {
        return royschedService.updateRoysched(royaltyId, requestDTO);
    }
}