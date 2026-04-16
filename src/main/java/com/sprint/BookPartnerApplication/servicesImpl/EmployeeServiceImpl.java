package com.sprint.BookPartnerApplication.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.BookPartnerApplication.entity.Employee;
import com.sprint.BookPartnerApplication.entity.Jobs;
import com.sprint.BookPartnerApplication.repository.EmployeeRepository;
import com.sprint.BookPartnerApplication.repository.JobsRepository;

@Service
public class EmployeeServiceImpl {

    @Autowired
    private EmployeeRepository repo;
    
    @Autowired
    private JobsRepository jobsRepository;

    public Employee save(Employee emp) {
        if (emp.getJob() != null && emp.getJob().getJobId() != null) {
            Jobs job = jobsRepository.findById(emp.getJob().getJobId()).orElse(null);
            if (job == null) {
                throw new IllegalArgumentException("Job with ID " + emp.getJob().getJobId() + " does not exist");
            }
            emp.setJob(job);
        }
        return repo.save(emp);
    }

    public List<Employee> getAll() {
        return repo.findAll();
    }

    public Employee getOne(String id) {
        return repo.findById(id).orElse(null);
    }

    public Employee update(Employee emp) {
        // If a job is provided, validate it exists in DB
        if (emp.getJob() != null && emp.getJob().getJobId() != null) {
            Jobs job = jobsRepository.findById(emp.getJob().getJobId()).orElse(null);
            if (job == null) {
                throw new IllegalArgumentException("Job with ID " + emp.getJob().getJobId() + " does not exist");
            }
            emp.setJob(job);
        }
        return repo.save(emp);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}