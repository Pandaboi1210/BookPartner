package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.EmployeeRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.EmployeeResponseDTO;
import com.sprint.BookPartnerApplication.entity.Employee;
import com.sprint.BookPartnerApplication.entity.Jobs;
import com.sprint.BookPartnerApplication.repository.EmployeeRepository;
import com.sprint.BookPartnerApplication.repository.JobsRepository;
import com.sprint.BookPartnerApplication.repository.PublishersRepository;
import com.sprint.BookPartnerApplication.servicesImpl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private JobsRepository jobsRepository;

    @Mock
    private PublishersRepository publishersRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeRequestDTO testEmployeeRequestDTO;
    private EmployeeResponseDTO testEmployeeResponseDTO;
    private Employee testEmployee;
    private Jobs testJob;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Test data - Jobs
        testJob = new Jobs();
        testJob.setJobId((short) 1);
        testJob.setJobDesc("Software Engineer");
        testJob.setMinLvl(5);
        testJob.setMaxLvl(10);
        
        // Test data - CREATE (RequestDTO)
        testEmployeeRequestDTO = new EmployeeRequestDTO();
        testEmployeeRequestDTO.setEmpId("EMP1001M");
        testEmployeeRequestDTO.setFname("Robert");
        testEmployeeRequestDTO.setLname("Smith");
        testEmployeeRequestDTO.setJobLvl(5);
        testEmployeeRequestDTO.setPubId("0877");
        testEmployeeRequestDTO.setJobId((short) 1);
        
        // Test data - Response DTO
        testEmployeeResponseDTO = new EmployeeResponseDTO();
        testEmployeeResponseDTO.setEmpId("EMP1001M");
        testEmployeeResponseDTO.setFname("Robert");
        testEmployeeResponseDTO.setLname("Smith");
        testEmployeeResponseDTO.setJobLvl(5);
        testEmployeeResponseDTO.setPubId("0877");
        testEmployeeResponseDTO.setJobDesc("Software Engineer");
        
        // Test data - Entity (for mocking repository)
        testEmployee = new Employee();
        testEmployee.setEmpId("EMP1001M");
        testEmployee.setFname("Robert");
        testEmployee.setLname("Smith");
        testEmployee.setJobLvl(5);
        testEmployee.setPubId("0877");
        testEmployee.setJob(testJob);
    }

    @Test
    public void testCreateEmployee() {
        when(employeeRepository.existsById("EMP1001M")).thenReturn(false);
        when(jobsRepository.findById((short) 1)).thenReturn(Optional.of(testJob));
        when(publishersRepository.existsById("0877")).thenReturn(true);
        when(employeeRepository.save(any(Employee.class))).thenReturn(testEmployee);
        
        EmployeeResponseDTO result = employeeService.createEmployee(testEmployeeRequestDTO);
        
        assertNotNull(result);
        assertEquals("EMP1001M", result.getEmpId());
        assertEquals("Robert", result.getFname());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    public void testGetEmployeeById() {
        when(employeeRepository.findById("EMP1001M")).thenReturn(java.util.Optional.of(testEmployee));
        
        EmployeeResponseDTO result = employeeService.getEmployeeById("EMP1001M");
        
        assertNotNull(result);
        assertEquals("EMP1001M", result.getEmpId());
        verify(employeeRepository, times(1)).findById("EMP1001M");
    }

    @Test
    public void testGetAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(testEmployee);
        
        when(employeeRepository.findAll()).thenReturn(employeeList);
        
        List<EmployeeResponseDTO> result = employeeService.getAllEmployees();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateEmployee() {
        EmployeeRequestDTO updatedEmployeeDTO = new EmployeeRequestDTO();
        updatedEmployeeDTO.setEmpId("EMP1001M");
        updatedEmployeeDTO.setFname("Richard");
        updatedEmployeeDTO.setLname("Johnson");
        updatedEmployeeDTO.setJobLvl(7);
        updatedEmployeeDTO.setPubId("0877");
        updatedEmployeeDTO.setJobId((short) 1);

        Employee updatedEmployee = new Employee();
        updatedEmployee.setEmpId("EMP1001M");
        updatedEmployee.setFname("Richard");
        updatedEmployee.setLname("Johnson");
        updatedEmployee.setJobLvl(7);
        updatedEmployee.setPubId("0877");
        updatedEmployee.setJob(testJob);

        when(employeeRepository.findById("EMP1001M")).thenReturn(java.util.Optional.of(testEmployee));
        when(jobsRepository.findById((short) 1)).thenReturn(java.util.Optional.of(testJob));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);
        
        EmployeeResponseDTO result = employeeService.updateEmployee("EMP1001M", updatedEmployeeDTO);
        
        assertNotNull(result);
        verify(employeeRepository, times(1)).findById("EMP1001M");
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    public void testGetEmployeesByPublisher() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(testEmployee);
        
        when(employeeRepository.findByPubId("0877")).thenReturn(employeeList);
        
        List<EmployeeResponseDTO> result = employeeService.getEmployeesByPublisher("0877");
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findByPubId("0877");
    }
}
