package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.TitleAuthorRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.TitleAuthorResponseDTO;
import com.sprint.BookPartnerApplication.services.TitleAuthorService;

import jakarta.validation.Valid; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/title-authors") 
public class TitleAuthorController {

    @Autowired
    private TitleAuthorService titleAuthorService;
    @PostMapping
    public TitleAuthorResponseDTO createTitleAuthor(@Valid @RequestBody TitleAuthorRequestDTO titleAuthorDTO) {
        return titleAuthorService.createTitleAuthor(titleAuthorDTO);
    }

    @DeleteMapping("/{auId}/{titleId}")
    public void deleteTitleAuthor(@PathVariable String auId, @PathVariable String titleId) 
    {    
        titleAuthorService.deleteByAuthorAndTitle(auId, titleId);
    }
}