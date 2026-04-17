package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.AuthorsRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.AuthorsResponseDTO;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.servicesImpl.AuthorsServiceImpl;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorsController {

    @Autowired
    private AuthorsServiceImpl authorsService;

    
    @PostMapping
    public AuthorsResponseDTO createAuthor(@Valid @RequestBody AuthorsRequestDTO dto) {
        return authorsService.createAuthor(dto);
    }

  
    @GetMapping
    public List<AuthorsResponseDTO> getAllAuthors() {
        return authorsService.getAllAuthors();
    }

  
    @GetMapping("/{id}")
    public AuthorsResponseDTO getAuthorById(@PathVariable String id) {
        return authorsService.getAuthorById(id);
    }

    @PutMapping("/{id}")
    public AuthorsResponseDTO updateAuthor(@PathVariable String id,
                                           @Valid @RequestBody AuthorsRequestDTO dto) {
        return authorsService.updateAuthor(id, dto);
    }

    
    @DeleteMapping("/{id}")
    public String deleteAuthor(@PathVariable String id) {
        authorsService.deleteAuthor(id);
        return "Author deleted successfully!";
    }

    @GetMapping("/{id}/titles")
    public List<Title> getTitlesByAuthor(@PathVariable String id) {
        return authorsService.getTitlesByAuthor(id);
    }
}