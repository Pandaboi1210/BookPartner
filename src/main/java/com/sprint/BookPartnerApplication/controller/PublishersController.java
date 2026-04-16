package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.entity.Publishers;
import com.sprint.BookPartnerApplication.servicesImpl.PublishersServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublishersController {

    @Autowired
    private PublishersServiceImpl publishersService;

  
    @PostMapping
    public Publishers createPublisher(@RequestBody Publishers publisher) {
        return publishersService.savePublisher(publisher);
    }

    @GetMapping
    public List<Publishers> getAllPublishers() {
        return publishersService.getAllPublishers();
    }


    @GetMapping("/{id}")
    public Publishers getPublisherById(@PathVariable String id) {
        return publishersService.getPublisherById(id);
    }

  
    @PutMapping("/{id}")
    public Publishers updatePublisher(@PathVariable String id, @RequestBody Publishers publisher) {
        publisher.setPubId(id);
        return publishersService.savePublisher(publisher);
    }


    @DeleteMapping("/{id}")
    public String deletePublisher(@PathVariable String id) {
        publishersService.deletePublisher(id);
        return "Publisher deleted successfully!";
    }
}