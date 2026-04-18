package com.sprint.BookPartnerApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.sprint.BookPartnerApplication.dto.request.EmployeeRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.EmployeeResponseDTO;
import com.sprint.BookPartnerApplication.services.EmployeeService;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @PostMapping
    public EmployeeResponseDTO create(@Valid @RequestBody EmployeeRequestDTO dto) {
        return service.createEmployee(dto);
    }

    @GetMapping
    public List<EmployeeResponseDTO> getAll() {
        return service.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeResponseDTO getById(@PathVariable String id) {
        return service.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    public EmployeeResponseDTO update(@PathVariable String id,
                                      @Valid @RequestBody EmployeeRequestDTO dto) {
        return service.updateEmployee(id, dto);
    }

    @GetMapping("/publisher/{pubId}")
    public List<EmployeeResponseDTO> getByPublisher(@PathVariable String pubId) {
        return service.getEmployeesByPublisher(pubId);
    }
}