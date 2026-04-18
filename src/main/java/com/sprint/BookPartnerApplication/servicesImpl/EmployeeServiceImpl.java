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
            throw new EmployeeException("Employee already exists");
        }

        Jobs job = jobRepo.findById(dto.getJobId())
                .orElseThrow(() -> new EmployeeException("Job not found"));

        if (!pubRepo.existsById(dto.getPubId())) {
            throw new EmployeeException("Publisher not found");
        }


        // Job level must fall within the job's allowed range
        if (dto.getJobLvl() < job.getMinLvl() || dto.getJobLvl() > job.getMaxLvl()) {
            throw new EmployeeException("Job level " + dto.getJobLvl()
                    + " is out of range for this job. Allowed: "
                    + job.getMinLvl() + " – " + job.getMaxLvl());
        }

        // DTO → Entity

        Employee emp = new Employee();
        emp.setEmpId(dto.getEmpId());
        emp.setFname(dto.getFname());
        emp.setMinit(dto.getMinit());
        emp.setLname(dto.getLname());
        emp.setJobLvl(dto.getJobLvl());
        emp.setPubId(dto.getPubId());
        emp.setJob(job);

        empRepo.save(emp);

        return mapToResponse(emp);
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return empRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(String empId) {

        Employee emp = empRepo.findById(empId)
                .orElseThrow(() -> new EmployeeException("Employee not found"));

        return mapToResponse(emp);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(String empId, EmployeeRequestDTO dto) {

        Employee emp = empRepo.findById(empId)
                .orElseThrow(() -> new EmployeeException("Employee not found"));

        emp.setFname(dto.getFname());
        emp.setMinit(dto.getMinit());
        emp.setLname(dto.getLname());
        emp.setJobLvl(dto.getJobLvl());

        if (dto.getJobId() != null) {
            Jobs job = jobRepo.findById(dto.getJobId())
                    .orElseThrow(() -> new EmployeeException("Job not found"));

            emp.setJob(job);


            // Validate job level against the new job's range
            if (dto.getJobLvl() < job.getMinLvl() || dto.getJobLvl() > job.getMaxLvl()) {
                throw new EmployeeException("Job level " + dto.getJobLvl()
                        + " is out of range for this job. Allowed: "
                        + job.getMinLvl() + " – " + job.getMaxLvl());
            }

        }

        empRepo.save(emp);

        return mapToResponse(emp);
    }

    @Override
    public List<EmployeeResponseDTO> getEmployeesByPublisher(String publisherId) {

        return empRepo.findByPubId(publisherId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private EmployeeResponseDTO mapToResponse(Employee emp) {

        EmployeeResponseDTO res = new EmployeeResponseDTO();

        res.setEmpId(emp.getEmpId());
        res.setFname(emp.getFname());
        res.setMinit(emp.getMinit());
        res.setLname(emp.getLname());
        res.setJobLvl(emp.getJobLvl());
        res.setPubId(emp.getPubId());

        if (emp.getJob() != null) {
            res.setJobDesc(emp.getJob().getJobDesc());
        }

        res.setHireDate(emp.getHireDate());

        return res;
    }
}