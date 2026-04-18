package com.sprint.BookPartnerApplication.dto.response;

import java.time.LocalDate;

public class EmployeeResponseDTO {

    private String empId;
    private String fname;
    private String minit;
    private String lname;
    private int jobLvl;
    private String pubId;
    private String jobDesc;
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
	public String getJobDesc() {
		return jobDesc;
	}
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	public LocalDate getHireDate() {
		return hireDate;
	}
	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

    
}