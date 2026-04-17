package com.sprint.BookPartnerApplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmployeeRequestDTO {

    @NotBlank
    private String empId;

    @NotBlank
    private String fname;

    private String minit;   // optional

    @NotBlank
    private String lname;

    @NotNull
    private Integer jobLvl;

    @NotBlank
    private String pubId;

<<<<<<< HEAD
    @NotNull
=======
    // 🔥 CHANGE HERE — remove @NotNull
>>>>>>> 4e7b7031e4d06b4436aeceab8a248e21dd8cf887
    private Short jobId;

    // Getters & Setters

    public String getEmpId() { return empId; }
    public void setEmpId(String empId) { this.empId = empId; }

    public String getFname() { return fname; }
    public void setFname(String fname) { this.fname = fname; }

    public String getMinit() { return minit; }
    public void setMinit(String minit) { this.minit = minit; }

    public String getLname() { return lname; }
    public void setLname(String lname) { this.lname = lname; }

    public Integer getJobLvl() { return jobLvl; }
    public void setJobLvl(Integer jobLvl) { this.jobLvl = jobLvl; }

    public String getPubId() { return pubId; }
    public void setPubId(String pubId) { this.pubId = pubId; }

    public Short getJobId() { return jobId; }
    public void setJobId(Short jobId) { this.jobId = jobId; }
}