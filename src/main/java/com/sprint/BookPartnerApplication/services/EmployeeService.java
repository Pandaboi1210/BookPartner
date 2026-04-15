package com.sprint.BookPartnerApplication.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.BookPartnerApplication.entity.Employee;
import com.sprint.BookPartnerApplication.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    // CREATE
    public Employee save(Employee emp) {
        return repo.save(emp);
    }


    // READ ONE 
    public Employee getOne(Object id) {
        return repo.findById((Long) id).orElse(null);
    }

    // UPDATE 
    public Employee update(Employee emp) {
        return repo.save(emp);
    }

    // DELETE 
    public void delete(Object id) {
        repo.deleteById((Long) id);
    }
}