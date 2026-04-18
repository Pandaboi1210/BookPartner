package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.PublishersRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.PublishersResponseDTO;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.entity.Employee;
import com.sprint.BookPartnerApplication.servicesImpl.PublisherServiceImpl;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/publishers")
public class PublishersController {

    @Autowired
    private PublisherServiceImpl publishersService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createPublisher(@Valid @RequestBody PublishersRequestDTO dto) {
        PublishersResponseDTO response = publishersService.createPublisher(dto);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Publisher created successfully");
        result.put("data", response);

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPublishers() {
        List<PublishersResponseDTO> list = publishersService.getAllPublishers();

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Publishers fetched successfully");
        result.put("data", list);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPublisherById(@PathVariable String id) {
        PublishersResponseDTO response = publishersService.getPublisherById(id);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Publisher fetched successfully");
        result.put("data", response);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updatePublisher(@PathVariable String id,
                                                               @Valid @RequestBody PublishersRequestDTO dto) {
        PublishersResponseDTO response = publishersService.updatePublisher(id, dto);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Publisher updated successfully");
        result.put("data", response);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePublisher(@PathVariable String id) {
        publishersService.deletePublisher(id);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Publisher deleted successfully");

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/titles")
    public ResponseEntity<Map<String, Object>> getTitlesByPublisher(@PathVariable String id) {
        List<Title> titles = publishersService.getTitlesByPublisher(id);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Titles fetched successfully");
        result.put("data", titles);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<Map<String, Object>> getEmployeesByPublisher(@PathVariable String id) {
        List<Employee> employees = publishersService.getEmployeesByPublisher(id);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Employees fetched successfully");
        result.put("data", employees);

        return ResponseEntity.ok(result);
    }
}