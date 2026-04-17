package com.sprint.BookPartnerApplication.dto.response;



import java.util.List;

public class PublishersResponseDTO {

    private String pubId;
    private String pubName;
    private String city;
    private String state;
    private String country;


    private List<String> titles;
    private List<String> employees;

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

    public List<String> getTitles() { return titles; }
    public void setTitles(List<String> titles) { this.titles = titles; }

    public List<String> getEmployees() { return employees; }
    public void setEmployees(List<String> employees) { this.employees = employees; }
}