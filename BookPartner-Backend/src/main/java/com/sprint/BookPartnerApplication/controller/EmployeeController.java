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

    // POST /api/v1/employees
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO requestDTO) {
        return created(service.createEmployee(requestDTO));
    }

    // GET /api/v1/employees
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        return ok(service.getAllEmployees());
    }

    // GET /api/v1/employees/{empId}
    @GetMapping("/{empId}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable String empId) {
        return ok(service.getEmployeeById(empId));
    }

    // PUT /api/v1/employees/{empId}
    @PutMapping("/{empId}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable String empId,
            @Valid @RequestBody EmployeeRequestDTO requestDTO) {
        return ok(service.updateEmployee(empId, requestDTO));
    }

    // GET /api/v1/employees/publisher/{pubId}
    @GetMapping("/publisher/{pubId}")
    public ResponseEntity<List<EmployeeResponseDTO>> getByPublisher(@PathVariable String pubId) {
        return ok(service.getEmployeesByPublisher(pubId));
    }

    // DELETE /api/v1/employees/{empId}
    @DeleteMapping("/{empId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String empId) {
        service.deleteEmployee(empId);
        return ResponseEntity.noContent().build();
    }
    //helper methods 
    private <T> ResponseEntity<T> ok(T data) {
        return ResponseEntity.ok(data);
    }

    private <T> ResponseEntity<T> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }
}