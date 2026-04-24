package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.EmployeeRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.EmployeeResponseDTO;
import com.sprint.BookPartnerApplication.services.EmployeeService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(
            @Valid @RequestBody EmployeeRequestDTO requestDTO) {

        EmployeeResponseDTO data = service.createEmployee(requestDTO);

        return created(data);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {

        List<EmployeeResponseDTO> data = service.getAllEmployees();

        return ok(data);
    }

    // GET BY ID
    @GetMapping("/{empId}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(
            @PathVariable String empId) {

        EmployeeResponseDTO data = service.getEmployeeById(empId);

        return ok(data);
    }

    // UPDATE
    @PutMapping("/{empId}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable String empId,
            @Valid @RequestBody EmployeeRequestDTO requestDTO) {

        EmployeeResponseDTO data =
                service.updateEmployee(empId, requestDTO);

        return ok(data);
    }

    // GET BY PUBLISHER (custom)
    @GetMapping("/publisher/{pubId}")
    public ResponseEntity<List<EmployeeResponseDTO>> getByPublisher(
            @PathVariable String pubId) {

        List<EmployeeResponseDTO> data =
                service.getEmployeesByPublisher(pubId);

        return ok(data);
    }
    
    // DELETE
    @DeleteMapping("/{empId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String empId) {
        service.deleteEmployee(empId);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Helper methods
    private <T> ResponseEntity<T> ok(T data) {
        return ResponseEntity.ok(data);
    }

    private <T> ResponseEntity<T> created(T data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(data);
    }
}