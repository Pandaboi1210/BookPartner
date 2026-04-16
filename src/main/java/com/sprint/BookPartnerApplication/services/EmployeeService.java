package com.sprint.BookPartnerApplication.services;
import java.util.List;
import com.sprint.BookPartnerApplication.entity.Employee;

public interface EmployeeService {

    Employee createEmployee(Employee emp);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(String empId);

    Employee updateEmployee(String empId, Employee emp);

    List<Employee> getEmployeesByPublisher(String publisherId);
}