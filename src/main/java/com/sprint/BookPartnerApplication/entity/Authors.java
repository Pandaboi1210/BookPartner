package com.sprint.BookPartnerApplication.entity;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "authors")
public class Authors {

    @Id
    @Column(name = "au_id")
    @NotNull(message = "Author ID cannot be null")
    @NotBlank(message = "Author ID cannot be empty")
    private String auId;

    @Column(name = "au_lname")
    @NotNull(message = "Last name cannot be null")
    @NotBlank(message = "Last name cannot be empty")
    private String auLname;

    @Column(name = "au_fname")
    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be empty")
    private String auFname;

    private String phone = "UNKNOWN";

    private String address;

    private String city;

    private String state;

    private String zip;

    private int contract;

    

    public String getAuId() { return auId; }
    public void setAuId(String auId) { this.auId = auId; }

    public String getAuLname() { return auLname; } 
    public void setAuLname(String auLname) { this.auLname = auLname; }

    public String getAuFname() { return auFname; }
    public void setAuFname(String auFname) { this.auFname = auFname; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }

    public int getContract() { return contract; }
    public void setContract(int contract) { this.contract = contract; }

   
  
}