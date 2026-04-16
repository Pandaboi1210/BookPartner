package com.sprint.BookPartnerApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sprint.BookPartnerApplication.entity.Employee;
import com.sprint.BookPartnerApplication.servicesImpl.EmployeeServiceImpl;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl service;

    @PostMapping
    public Employee save(@RequestBody Employee emp) {
        return service.save(emp);
    }

    @GetMapping
    public List<Employee> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable String id) {
        return service.getOne(id);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee emp) {
        emp.setEmpId(id);
        return service.update(emp);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "Employee deleted successfully!";
    }
}