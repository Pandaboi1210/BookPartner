package com.sprint.BookPartnerApplication.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.BookPartnerApplication.entity.Employee;
import com.sprint.BookPartnerApplication.entity.Jobs;
import com.sprint.BookPartnerApplication.exception.DuplicateResourceException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
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

        // 🚨 409 CONFLICT: Employee already exists
        if (emp.getEmpId() != null && empRepo.existsById(emp.getEmpId())) {
            throw new DuplicateResourceException("Employee already exists with id: " + emp.getEmpId());
        }

        // 🚨 404 NOT FOUND: Job doesn't exist
        Jobs job = jobRepo.findById(emp.getJob().getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + emp.getJob().getJobId()));

        // 🚨 404 NOT FOUND: Publisher doesn't exist
        if (!pubRepo.existsById(emp.getPubId())) {
            throw new ResourceNotFoundException("Publisher not found with id: " + emp.getPubId());
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
        // 🚨 404 NOT FOUND
        return empRepo.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + empId));
    }

    @Override
    public Employee updateEmployee(String empId, Employee emp) {

        // This automatically throws ResourceNotFoundException if missing!
        Employee existing = getEmployeeById(empId);

        existing.setFname(emp.getFname());
        existing.setLname(emp.getLname());
        existing.setJobLvl(emp.getJobLvl());

        if (emp.getJob() != null) {
            // 🚨 404 NOT FOUND
            Jobs job = jobRepo.findById(emp.getJob().getJobId())
                    .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + emp.getJob().getJobId()));
            existing.setJob(job);
        }

        return empRepo.save(existing);
    }

    @Override
    public List<Employee> getEmployeesByPublisher(String publisherId) {
        // Extra safeguard: Check if publisher exists first
        if (!pubRepo.existsById(publisherId)) {
            throw new ResourceNotFoundException("Publisher not found with id: " + publisherId);
        }
        return empRepo.findByPubId(publisherId);
    }
}