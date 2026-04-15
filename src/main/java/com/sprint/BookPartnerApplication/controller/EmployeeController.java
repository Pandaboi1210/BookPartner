package com.sprint.BookPartnerApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sprint.BookPartnerApplication.entity.Employee;
import com.sprint.BookPartnerApplication.services.EmployeeService;

@RestController
@RequestMapping("/test")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @PostMapping
    public Employee save(@RequestBody Employee emp) {
        return service.save(emp);
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable Object id) {
        return service.getOne(id);
    }

    @PutMapping
    public Employee update(@RequestBody Employee emp) {
        return service.update(emp);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Object id) {
        service.delete(id);
        return "Deleted";
    }
}