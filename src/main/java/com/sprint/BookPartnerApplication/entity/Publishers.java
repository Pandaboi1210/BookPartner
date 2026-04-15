package com.sprint.BookPartnerApplication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "publishers")
public class Publishers {

    @Id
    @Column(name = "pub_id")
    @NotNull(message = "Publisher ID cannot be null")
    @NotBlank(message = "Publisher ID cannot be empty")
    private String pubId;

    @Column(name = "pub_name")
    @NotNull(message = "Publisher name cannot be null")
    @NotBlank(message = "Publisher name cannot be empty")
    private String pubName;

    private String city;

    private String state;

    private String country = "USA";

   

    public String getPubId() { return pubId; }
    public void setPubId(String pubId) { this.pubId = pubId; }

    public String getPubName() { return pubName; }
    public void setPubName(String pubName) { this.pubName = pubName; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}