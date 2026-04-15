package com.sprint.BookPartnerApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "emp_id", length = 10)
    @NotBlank(message = "Employee ID cannot be empty")
    @Pattern(
        regexp = "^[A-Z][A-Z][A-Z][1-9][0-9]{4}[FM]$|^[A-Z]-[A-Z][1-9][0-9]{4}[FM]$",
        message = "Invalid employee ID format"
    )
    private String empId;

    @NotBlank(message = "First name cannot be empty")
    @Column(name = "fname", nullable = false, length = 20)
    private String fname;

    @Column(name = "minit", length = 1)
    private String minit;

    @NotBlank(message = "Last name cannot be empty")
    @Column(name = "lname", nullable = false, length = 30)
    private String lname;


    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Jobs job;

    @Column(name = "job_lvl")
    private Integer jobLvl = 10;

    @Column(name = "pub_id", length = 4)
    private String pubId = "9952"; // simplified (can map later)

    @Column(name = "hire_date")
    private LocalDateTime hireDate = LocalDateTime.now();

    // Constructors
    public Employee() {}

    // Getters & Setters
    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMinit() {
        return minit;
    }

    public void setMinit(String minit) {
        this.minit = minit;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Jobs getJob() {
        return job;
    }

    public void setJob(Jobs job) {
        this.job = job;
    }

    public Integer getJobLvl() {
        return jobLvl;
    }

    public void setJobLvl(Integer jobLvl) {
        this.jobLvl = jobLvl;
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
    }

    public LocalDateTime getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDateTime hireDate) {
        this.hireDate = hireDate;
    }
}