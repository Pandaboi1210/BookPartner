package com.sprint.BookPartnerApplication.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PublishersRequestDTO {

    @NotBlank(message = "Publisher ID cannot be empty")
    @Pattern(regexp = "^[0-9]{4}$", message = "Publisher ID must be 4 digits")
    private String pubId;

    @NotBlank(message = "Publisher name cannot be empty")
    private String pubName;

    private String city;
    private String state;

    private String country = "USA";

    // Getters & Setters
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