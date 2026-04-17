package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.PublishersRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.PublishersResponseDTO;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.entity.Employee;
import com.sprint.BookPartnerApplication.servicesImpl.PublisherServiceImpl;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
public class PublishersController {

    @Autowired
    private PublisherServiceImpl publishersService;


    @PostMapping
    public PublishersResponseDTO createPublisher(@Valid @RequestBody PublishersRequestDTO dto) {
        return publishersService.createPublisher(dto);
    }


    @GetMapping
    public List<PublishersResponseDTO> getAllPublishers() {
        return publishersService.getAllPublishers();
    }

    
    @GetMapping("/{id}")
    public PublishersResponseDTO getPublisherById(@PathVariable String id) {
        return publishersService.getPublisherById(id);
    }


    @PutMapping("/{id}")
    public PublishersResponseDTO updatePublisher(@PathVariable String id,
                                                 @Valid @RequestBody PublishersRequestDTO dto) {
        return publishersService.updatePublisher(id, dto);
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