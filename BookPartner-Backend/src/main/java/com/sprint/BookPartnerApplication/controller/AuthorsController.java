package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.AuthorsRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.AuthorsResponseDTO;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.servicesImpl.AuthorsServiceImpl;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorsController 
{

    @Autowired
    private AuthorsServiceImpl authorsService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createAuthor(@Valid @RequestBody AuthorsRequestDTO dto) {
        AuthorsResponseDTO response = authorsService.createAuthor(dto);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Author created successfully");
        result.put("data", response);

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllAuthors(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) Integer contract) {

        List<AuthorsResponseDTO> list = authorsService.getAuthorsByFilter(city, state, contract);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Authors fetched successfully");
        result.put("data", list);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAuthorById(@PathVariable String id) {
        AuthorsResponseDTO response = authorsService.getAuthorById(id);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Author fetched successfully");
        result.put("data", response);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAuthor(@PathVariable String id,
                                                            @Valid @RequestBody AuthorsRequestDTO dto) {
        AuthorsResponseDTO response = authorsService.updateAuthor(id, dto);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Author updated successfully");
        result.put("data", response);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteAuthor(@PathVariable String id) {
        authorsService.deleteAuthor(id);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Author deleted successfully");

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/titles")
    public ResponseEntity<Map<String, Object>> getTitlesByAuthor(@PathVariable String id) {
        List<Title> titles = authorsService.getTitlesByAuthor(id);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Titles fetched successfully");
        result.put("data", titles);

        return ResponseEntity.ok(result);
    }
}