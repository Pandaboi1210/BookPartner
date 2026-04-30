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

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {

        if (empRepo.existsById(dto.getEmpId())) {
            throw new EmployeeException("Employee already exists with ID: " + dto.getEmpId());
        }

        Jobs job = jobRepo.findById(dto.getJobId())
                .orElseThrow(() -> new EmployeeException("Job not found with ID: " + dto.getJobId()));

        if (!pubRepo.existsById(dto.getPubId())) {
            throw new EmployeeException("Publisher not found with ID: " + dto.getPubId());
        }

        Employee emp = new Employee();

        emp.setEmpId(dto.getEmpId());
        emp.setFname(dto.getFname());

        // no NULL minit
        if (dto.getMinit() == null || dto.getMinit().isEmpty()) {
            emp.setMinit("");
        } else {
            emp.setMinit(dto.getMinit());
        }

        emp.setLname(dto.getLname());
        emp.setJobLvl(dto.getJobLvl());
        emp.setPubId(dto.getPubId());
        emp.setJob(job);

        // ✅ hire date (date only)
        emp.setHireDate(dto.getHireDate());

        Employee saved = empRepo.save(emp);

        return mapToDTO(saved);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return empRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(String empId) {
        Employee emp = empRepo.findById(empId)
                .orElseThrow(() -> new EmployeeException("Employee not found with ID: " + empId));

        return mapToDTO(emp);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(String empId, EmployeeRequestDTO dto) {

        Employee existing = empRepo.findById(empId)
                .orElseThrow(() -> new EmployeeException("Employee not found with ID: " + empId));

        existing.setFname(dto.getFname());

        // ✅ FIX: no NULL minit
        if (dto.getMinit() == null || dto.getMinit().isEmpty()) {
            existing.setMinit("");
        } else {
            existing.setMinit(dto.getMinit());
        }

        existing.setLname(dto.getLname());
        existing.setJobLvl(dto.getJobLvl());

        if (dto.getJobId() != null) {
            Jobs job = jobRepo.findById(dto.getJobId())
                    .orElseThrow(() -> new EmployeeException("Job not found with ID: " + dto.getJobId()));
            existing.setJob(job);
        }

        existing.setHireDate(dto.getHireDate());
        existing.setPubId(dto.getPubId());

        Employee updated = empRepo.save(existing);

        return mapToDTO(updated);
    }

    @Override
    public List<EmployeeResponseDTO> getEmployeesByPublisher(String pubId) {
        return empRepo.findByPubId(pubId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Mapper method
    private EmployeeResponseDTO mapToDTO(Employee emp) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        dto.setEmpId(emp.getEmpId());
        dto.setFname(emp.getFname());
        dto.setMinit(emp.getMinit());
        dto.setLname(emp.getLname());
        dto.setJobLvl(emp.getJobLvl());
        dto.setPubId(emp.getPubId());
        dto.setHireDate(emp.getHireDate());

        if (emp.getJob() != null) {
            dto.setJobId(Integer.valueOf(emp.getJob().getJobId()));
            dto.setJobDesc(emp.getJob().getJobDesc());
        }

        return dto;
    }
    @Override
    public void deleteEmployee(String empId) {
        if (!empRepo.existsById(empId)) {
            throw new EmployeeException("Employee not found with ID: " + empId);
        }
        empRepo.deleteById(empId);
    }
}