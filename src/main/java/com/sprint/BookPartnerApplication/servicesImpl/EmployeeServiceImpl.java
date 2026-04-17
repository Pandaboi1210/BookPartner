package com.sprint.BookPartnerApplication.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.BookPartnerApplication.entity.Employee;
import com.sprint.BookPartnerApplication.entity.Jobs;
import com.sprint.BookPartnerApplication.exception.EmployeeException;
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
            throw new EmployeeException("Employee already exists with id: " + emp.getEmpId());
        }

        Jobs job = jobRepo.findById(emp.getJob().getJobId())
                .orElseThrow(() -> new EmployeeException("Job not found"));

        if (!pubRepo.existsById(emp.getPubId())) {
            throw new EmployeeException("Publisher not found");
        }

        emp.setJob(job);

        // 🔥 USING CUSTOM INSERT
        empRepo.insertEmployee(
                emp.getEmpId(),
                emp.getFname(),
                emp.getLname(),
                emp.getJobLvl(),
                emp.getPubId(),
                emp.getJob().getJobId()
        );

        return emp;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return empRepo.findAll();
    }

    @Override
    public Employee getEmployeeById(String empId) {
        return empRepo.findById(empId)
                .orElseThrow(() -> new EmployeeException("Employee not found with id: " + empId));
    }

    @Override
    public Employee updateEmployee(String empId, Employee emp) {

        Employee existing = getEmployeeById(empId);

        // 🔥 USING CUSTOM UPDATE
        empRepo.updateEmployeeQuery(
                empId,
                emp.getFname(),
                emp.getLname(),
                emp.getJobLvl()
        );

        return existing;
    }

    @Override
    public List<Employee> getEmployeesByPublisher(String publisherId) {
        return empRepo.findByPubId(publisherId);
    }
}