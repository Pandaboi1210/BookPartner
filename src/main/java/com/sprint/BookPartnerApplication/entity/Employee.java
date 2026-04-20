package com.sprint.BookPartnerApplication.entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "emp_id")
    private String empId;

    @Column(name = "fname", nullable = false)
    private String fname;

    //NO NULL 
    @Column(name = "minit", nullable = false)
    private String minit = "";

    @Column(name = "lname", nullable = false)
    private String lname;

    @Column(name = "job_lvl")
    private int jobLvl;

    @Column(name = "pub_id")
    private String pubId;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Jobs job;

    //Date
    @Column(name = "hire_date")
    private LocalDate hireDate;

    // Getters & Setters

    public String getEmpId() { return empId; }
    public void setEmpId(String empId) { this.empId = empId; }

    public String getFname() { return fname; }
    public void setFname(String fname) { this.fname = fname; }

    public String getMinit() { return minit; }
    public void setMinit(String minit) { this.minit = minit; }

    public String getLname() { return lname; }
    public void setLname(String lname) { this.lname = lname; }

    public int getJobLvl() { return jobLvl; }
    public void setJobLvl(int jobLvl) { this.jobLvl = jobLvl; }

    public String getPubId() { return pubId; }
    public void setPubId(String pubId) { this.pubId = pubId; }

    public Jobs getJob() { return job; }
    public void setJob(Jobs job) { this.job = job; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
}