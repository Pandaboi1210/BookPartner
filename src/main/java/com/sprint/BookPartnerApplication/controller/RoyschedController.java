package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.RoyschedRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.RoyschedResponseDTO;
import com.sprint.BookPartnerApplication.services.RoyschedService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/royalties")
public class RoyschedController {

    private final RoyschedService royschedService;

    public RoyschedController(RoyschedService royschedService) {
        this.royschedService = royschedService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<RoyschedResponseDTO> createRoysched(
            @Valid @RequestBody RoyschedRequestDTO requestDTO) {

        RoyschedResponseDTO data =
                royschedService.createRoysched(requestDTO);

        return created(data);
    }

    // GET BY TITLE
    @GetMapping("/title/{titleId}")
    public ResponseEntity<List<RoyschedResponseDTO>> getRoyschedByTitle(
            @PathVariable String titleId) {

        List<RoyschedResponseDTO> data =
                royschedService.getRoyschedByTitle(titleId);

        return ok(data);
    }

    // UPDATE
    @PutMapping("/{royaltyId}")
    public ResponseEntity<RoyschedResponseDTO> updateRoysched(
            @PathVariable Integer royaltyId,
            @Valid @RequestBody RoyschedRequestDTO requestDTO) {

        RoyschedResponseDTO data =
                royschedService.updateRoysched(
                        royaltyId,
                        requestDTO);

        return ok(data);
    }
    private <T> ResponseEntity<T> ok(T data) {
        return ResponseEntity.ok(data);
    }
    private <T> ResponseEntity<T> created(T data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(data);
    }
}