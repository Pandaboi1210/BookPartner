package com.sprint.BookPartnerApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sprint.BookPartnerApplication.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
	List<Employee> findByPubId(String pubId);
}