package com.sprint.BookPartnerApplication.servicesImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.BookPartnerApplication.entity.Employee;
import com.sprint.BookPartnerApplication.entity.Jobs;
import com.sprint.BookPartnerApplication.repository.EmployeeRepository;
import com.sprint.BookPartnerApplication.repository.JobsRepository;
import com.sprint.BookPartnerApplication.repository.PublishersRepository;
import com.sprint.BookPartnerApplication.services.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository empRepo;

    @Autowired
    private JobsRepository jobRepo;

    @Autowired
    private PublishersRepository pubRepo;

    @Override
    public Employee createEmployee(Employee emp) {

        if (empRepo.existsById(emp.getEmpId())) {
            throw new RuntimeException("Employee already exists");
        }

        Jobs job = jobRepo.findById(emp.getJob().getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!pubRepo.existsById(emp.getPubId())) {
            throw new RuntimeException("Publisher not found");
        }

        emp.setJob(job);
        return empRepo.save(emp);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return empRepo.findAll();
    }

    @Override
    public Employee getEmployeeById(String empId) {
        return empRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    public Employee updateEmployee(String empId, Employee emp) {

        Employee existing = getEmployeeById(empId);

        existing.setFname(emp.getFname());
        existing.setLname(emp.getLname());
        existing.setJobLvl(emp.getJobLvl());

        if (emp.getJob() != null) {
            Jobs job = jobRepo.findById(emp.getJob().getJobId())
                    .orElseThrow(() -> new RuntimeException("Job not found"));
            existing.setJob(job);
        }

        return empRepo.save(existing);
    }

    @Override
    public List<Employee> getEmployeesByPublisher(String publisherId) {
        return empRepo.findByPubId(publisherId);
    }
}