package com.sprint.BookPartnerApplication.dto.request;

import java.time.LocalDate;

public class EmployeeRequestDTO {

    private String empId;
    private String fname;
    private String minit;
    private String lname;
    private int jobLvl;
    private String pubId;
    private Short jobId;
    private LocalDate hireDate;
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
	public int getJobLvl() {
		return jobLvl;
	}
	public void setJobLvl(int jobLvl) {
		this.jobLvl = jobLvl;
	}
	public String getPubId() {
		return pubId;
	}
	public void setPubId(String pubId) {
		this.pubId = pubId;
	}
	public Short getJobId() {
		return jobId;
	}
	public void setJobId(Short jobId) {
		this.jobId = jobId;
	}
	public LocalDate getHireDate() {
		return hireDate;
	}
	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

   
}