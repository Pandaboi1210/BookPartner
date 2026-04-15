package com.sprint.BookPartnerApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "jobs")
public class Jobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Job title cannot be empty")
    @Column(name = "title")
    private String title;

    @NotNull(message = "Salary cannot be null")
    @Positive(message = "Salary must be positive")
    @Column(name = "salary")
    private Double salary;

    @NotBlank(message = "Location cannot be empty")
    @Column(name = "location")
    private String location;

    // Constructors
    public Jobs() {}

    public Jobs(String title, Double salary, String location) {
        this.title = title;
        this.salary = salary;
        this.location = location;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}