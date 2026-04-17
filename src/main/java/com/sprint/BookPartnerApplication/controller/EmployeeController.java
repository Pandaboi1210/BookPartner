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

    // CREATE
    @PostMapping
    public EmployeeResponseDTO create(
            @Valid @RequestBody EmployeeRequestDTO dto) {

        return service.createEmployee(dto);
    }

    // GET ALL
    @GetMapping
    public List<EmployeeResponseDTO> getAll() {
        return service.getAllEmployees();
    }

    // GET BY ID
    @GetMapping("/{employeeId}")
    public EmployeeResponseDTO getById(
            @PathVariable String employeeId) {

        return service.getEmployeeById(employeeId);
    }

    //UPDATE
    @PutMapping("/{employeeId}")
    public EmployeeResponseDTO update(
            @PathVariable String employeeId,
            @Valid @RequestBody EmployeeRequestDTO dto) {

        return service.updateEmployee(employeeId, dto);
    }

    // CUSTOM QUERY
    @GetMapping("/publisher/{publisherId}")
    public List<EmployeeResponseDTO> getByPublisher(
            @PathVariable String publisherId) {

        return service.getEmployeesByPublisher(publisherId);
    }
}