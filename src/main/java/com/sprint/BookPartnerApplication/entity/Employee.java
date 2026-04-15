

package com.sprint.BookPartnerApplication.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "emp_id")
    private String empId;

    @Column(name = "fname", nullable = false)
    private String fname;

    @Column(name = "minit")
    private String minit;

    @Column(name = "lname", nullable = false)
    private String lname;

    @Column(name = "job_lvl")
    private Integer jobLvl;

    @Column(name = "hire_date")
    private LocalDateTime hireDate;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Jobs job;

    @ManyToOne
    @JoinColumn(name = "pub_id", nullable = false)
    private Publishers publisher;

    public Employee() {}

    public Employee(String empId, String fname, String minit, String lname,
                    Integer jobLvl, LocalDateTime hireDate,
                    Jobs job, Publishers publisher) {
        this.empId = empId;
        this.fname = fname;
        this.minit = minit;
        this.lname = lname;
        this.jobLvl = jobLvl;
        this.hireDate = hireDate;
        this.job = job;
        this.publisher = publisher;
    }

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

    public Integer getJobLvl() {
        return jobLvl;
    }

    public void setJobLvl(Integer jobLvl) {
        this.jobLvl = jobLvl;
    }

    public LocalDateTime getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDateTime hireDate) {
        this.hireDate = hireDate;
    }

    public Jobs getJob() {
        return job;
    }

    public void setJob(Jobs job) {
        this.job = job;
    }

    public Publishers getPublisher() {
        return publisher;
    }

    public void setPublisher(Publishers publisher) {
        this.publisher = publisher;
    }
}


