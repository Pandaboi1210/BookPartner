package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.entity.Authors;
import com.sprint.BookPartnerApplication.entity.Title;

import com.sprint.BookPartnerApplication.servicesImpl.AuthorsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorsServiceImpl authorsService;

 
    @PostMapping
    public Authors createAuthor(@RequestBody Authors author) {
        return authorsService.createAuthor(author);
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
        return authorsService.updateAuthor(id, author);
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