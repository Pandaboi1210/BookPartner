package com.sprint.BookPartnerApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sprint.BookPartnerApplication.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    List<Employee> findByPubId(String pubId);

    //CUSTOM INSERT
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO employee (emp_id, fname, lname, job_lvl, pub_id, job_id) VALUES (:id, :fname, :lname, :lvl, :pubId, :jobId)", nativeQuery = true)
    void insertEmployee(@Param("id") String id,
                        @Param("fname") String fname,
                        @Param("lname") String lname,
                        @Param("lvl") int lvl,
                        @Param("pubId") String pubId,
                        @Param("jobId") Short jobId);

    //CUSTOM UPDATE
    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.fname = :fname, e.lname = :lname, e.jobLvl = :lvl WHERE e.empId = :id")
    void updateEmployeeQuery(@Param("id") String id,
                             @Param("fname") String fname,
                             @Param("lname") String lname,
                             @Param("lvl") int lvl);
}