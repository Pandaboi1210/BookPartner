package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.TitleAuthorRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.TitleAuthorResponseDTO;
import com.sprint.BookPartnerApplication.services.TitleAuthorService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/title-authors") 
public class TitleAuthorController {

    @Autowired
    private TitleAuthorService titleAuthorService;

    // CREATE
    @PostMapping
    public ResponseEntity<TitleAuthorResponseDTO> createTitleAuthor(@Valid @RequestBody TitleAuthorRequestDTO titleAuthorDTO) {
        TitleAuthorResponseDTO data = titleAuthorService.createTitleAuthor(titleAuthorDTO);
        
        // Returns a 201 CREATED status
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(data);
    }

    // DELETE
    @DeleteMapping("/{auId}/{titleId}")
    public ResponseEntity<Void> deleteTitleAuthor(@PathVariable String auId, @PathVariable String titleId) {    
        titleAuthorService.deleteByAuthorAndTitle(auId, titleId);
        
        // Returns a 204 NO CONTENT status (Standard for Delete requests)
        return ResponseEntity.noContent().build(); 
    }
}