package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.TitleRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.TitleResponseDTO;
import com.sprint.BookPartnerApplication.entity.Authors;
import com.sprint.BookPartnerApplication.entity.Roysched;
import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.services.TitleService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/titles")
public class TitleController {

    @Autowired
    private TitleService titleService;

    @GetMapping
    public ResponseEntity<List<TitleResponseDTO>> getTitles(
            @RequestParam(required = false) String type, 
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) Double minPrice, 
            @RequestParam(required = false) Double maxPrice) {
        
        boolean anyFilter =
                (type != null && !type.isBlank()) ||
                (publisher != null && !publisher.isBlank()) ||
                (minPrice != null) ||
                (maxPrice != null);

        List<TitleResponseDTO> data = anyFilter
                ? titleService.getTitlesFiltered(type, publisher, minPrice, maxPrice)
                : titleService.getAllTitles();

        return ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TitleResponseDTO> getTitleById(@PathVariable String id) {
        TitleResponseDTO data = titleService.getTitleById(id);
        return ok(data);
    }

    @PostMapping
    public ResponseEntity<TitleResponseDTO> createTitle(@Valid @RequestBody TitleRequestDTO titleDTO) {
        TitleResponseDTO data = titleService.insertTitle(titleDTO); 
        return created(data); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<TitleResponseDTO> updateTitle(@PathVariable String id, @Valid @RequestBody TitleRequestDTO titleDTO) { 
        TitleResponseDTO data = titleService.updateTitleById(id, titleDTO); 
        return ok(data); 
    }

    @GetMapping("/{titleId}/authors")
    public ResponseEntity<List<Authors>> getAuthorsByTitle(@PathVariable String titleId) {
        List<Authors> data = titleService.getAuthorsByTitle(titleId);
        return ok(data);
    }

    @GetMapping("/{titleId}/sales")
    public ResponseEntity<List<Sales>> getSalesByTitle(@PathVariable String titleId) {
        List<Sales> data = titleService.getSalesByTitleId(titleId);
        return ok(data);
    }

    @GetMapping("/{titleId}/royalties")
    public ResponseEntity<List<Roysched>> getRoyaltiesByTitle(@PathVariable String titleId) {
        List<Roysched> data = titleService.getRoyaltiesByTitleId(titleId);
        return ok(data);
    }

    // --- Helper Methods ---

    private <T> ResponseEntity<T> ok(T data) {
        return ResponseEntity.ok(data);
    }

    private <T> ResponseEntity<T> created(T data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(data);
    }
}