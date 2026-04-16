package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.entity.Publishers;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.entity.Employee;
import com.sprint.BookPartnerApplication.servicesImpl.PublisherServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublishersController {

    @Autowired
    private PublisherServiceImpl publishersService;

    @PostMapping
    public Publishers createPublisher(@RequestBody Publishers publisher) {
        return publishersService.createPublisher(publisher);
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
        return publishersService.updatePublisher(id, publisher);
    }


    @DeleteMapping("/{id}")
    public String deletePublisher(@PathVariable String id) {
        publishersService.deletePublisher(id);
        return "Publisher deleted successfully!";
    }

    @GetMapping("/{id}/titles")
    public List<Title> getTitlesByPublisher(@PathVariable String id) {
        return publishersService.getTitlesByPublisher(id);
    }


    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesByPublisher(@PathVariable String id) {
        return publishersService.getEmployeesByPublisher(id);
    }
}