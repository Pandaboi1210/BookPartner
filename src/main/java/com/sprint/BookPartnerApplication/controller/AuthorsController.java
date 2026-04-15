package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.entity.Authors;
import com.sprint.BookPartnerApplication.services.AuthorsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorsService authorsService;

    @PostMapping
    public Authors createAuthor(@RequestBody Authors author) {
        return authorsService.saveAuthor(author);
    }

    @GetMapping
    public List<Authors> getAllAuthors() {
        return authorsService.getAllAuthors();
    }

    
    @GetMapping("/{id}")
    public Authors getAuthorById(@PathVariable String id) {
        return authorsService.getAuthorById(id);
    }

    @PutMapping("/{id}")
    public Authors updateAuthor(@PathVariable String id, @RequestBody Authors author) {
        author.setAuId(id); 
        return authorsService.saveAuthor(author);
    }

  
    @DeleteMapping("/{id}")
    public String deleteAuthor(@PathVariable String id) {
        authorsService.deleteAuthor(id);
        return "Author deleted successfully!";
    }
}