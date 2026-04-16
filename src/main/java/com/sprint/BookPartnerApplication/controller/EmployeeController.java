package com.sprint.BookPartnerApplication.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sprint.BookPartnerApplication.entity.Employee;
import com.sprint.BookPartnerApplication.services.EmployeeService;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    // POST /employees
    @PostMapping
    public Employee create(@RequestBody Employee emp) {
        return service.createEmployee(emp);
    }

    // GET /employees
    @GetMapping
    public List<Employee> getAll() {
        return service.getAllEmployees();
    }

    // GET /employees/{employeeId}
    @GetMapping("/{employeeId}")
    public Employee getById(@PathVariable String employeeId) {
        return service.getEmployeeById(employeeId);
    }

    //Update
    @PutMapping("/{employeeId}")
    public Employee update(@PathVariable String employeeId, @RequestBody Employee emp) {
        return service.updateEmployee(employeeId, emp);
    }

    // Custom method
    @GetMapping("/publisher/{publisherId}")
    public List<Employee> getByPublisher(@PathVariable String publisherId) {
        return service.getEmployeesByPublisher(publisherId);
    }
}