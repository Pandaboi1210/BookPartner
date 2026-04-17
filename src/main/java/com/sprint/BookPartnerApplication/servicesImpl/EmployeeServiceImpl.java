package com.sprint.BookPartnerApplication.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.BookPartnerApplication.dto.request.EmployeeRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.EmployeeResponseDTO;
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

    // 🔥 CREATE
    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {

        if (empRepo.existsById(dto.getEmpId())) {
            throw new EmployeeException("Employee already exists with id: " + dto.getEmpId());
        }

        Jobs job = jobRepo.findById(dto.getJobId())
                .orElseThrow(() -> new EmployeeException("Job not found"));

        if (!pubRepo.existsById(dto.getPubId())) {
            throw new EmployeeException("Publisher not found");
        }

        // DTO → Entity
        Employee emp = new Employee();
        emp.setEmpId(dto.getEmpId());
        emp.setFname(dto.getFname());
        emp.setLname(dto.getLname());
        emp.setJobLvl(dto.getJobLvl());
        emp.setPubId(dto.getPubId());
        emp.setJob(job);

        empRepo.save(emp);

        // Entity → ResponseDTO
        return mapToResponse(emp);
    }

    // 🔥 GET ALL
    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return empRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 🔥 GET BY ID
    @Override
    public EmployeeResponseDTO getEmployeeById(String empId) {

        Employee emp = empRepo.findById(empId)
                .orElseThrow(() -> new EmployeeException("Employee not found with id: " + empId));

        return mapToResponse(emp);
    }

    // 🔥 UPDATE
    @Override
    public EmployeeResponseDTO updateEmployee(String empId, EmployeeRequestDTO dto) {

        Employee existing = empRepo.findById(empId)
                .orElseThrow(() -> new EmployeeException("Employee not found"));

        existing.setFname(dto.getFname());
        existing.setLname(dto.getLname());
        existing.setJobLvl(dto.getJobLvl());

        if (dto.getJobId() != null) {
            Jobs job = jobRepo.findById(dto.getJobId())
                    .orElseThrow(() -> new EmployeeException("Job not found"));
            existing.setJob(job);
        }

        empRepo.save(existing);

        return mapToResponse(existing);
    }

    // 🔥 CUSTOM METHOD
    @Override
    public List<EmployeeResponseDTO> getEmployeesByPublisher(String publisherId) {

        return empRepo.findByPubId(publisherId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 🔥 COMMON MAPPING METHOD
    private EmployeeResponseDTO mapToResponse(Employee emp) {

        EmployeeResponseDTO res = new EmployeeResponseDTO();

        res.setEmpId(emp.getEmpId());
        res.setFname(emp.getFname());
        res.setLname(emp.getLname());
        res.setJobLvl(emp.getJobLvl());
        res.setPubId(emp.getPubId());

        if (emp.getJob() != null) {
            res.setJobDesc(emp.getJob().getJobDesc());
        }

        return res;
    }

	

	
}