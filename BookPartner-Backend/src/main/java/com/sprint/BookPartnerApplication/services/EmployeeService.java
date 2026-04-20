package com.sprint.BookPartnerApplication.services;

import java.util.List;

import com.sprint.BookPartnerApplication.dto.request.EmployeeRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.EmployeeResponseDTO;

public interface EmployeeService {

    EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto);

    List<EmployeeResponseDTO> getAllEmployees();

    EmployeeResponseDTO getEmployeeById(String empId);

    EmployeeResponseDTO updateEmployee(String empId, EmployeeRequestDTO dto);

    List<EmployeeResponseDTO> getEmployeesByPublisher(String publisherId);
}