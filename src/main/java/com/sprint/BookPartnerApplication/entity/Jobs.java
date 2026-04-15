package com.sprint.BookPartnerApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "jobs")
public class Jobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Short jobId;

    @NotBlank(message = "Job description cannot be empty")
    @Column(name = "job_desc", nullable = false, length = 50)
    private String jobDesc;

    @Min(value = 10, message = "Minimum level must be >= 10")
    @Column(name = "min_lvl", nullable = false)
    private Integer minLvl;

    @Max(value = 250, message = "Maximum level must be <= 250")
    @Column(name = "max_lvl", nullable = false)
    private Integer maxLvl;

    // Constructors
    public Jobs() {}

    // Getters & Setters
    public Short getJobId() {
        return jobId;
    }

    public void setJobId(Short jobId) {
        this.jobId = jobId;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public Integer getMinLvl() {
        return minLvl;
    }

    public void setMinLvl(Integer minLvl) {
        this.minLvl = minLvl;
    }

    public Integer getMaxLvl() {
        return maxLvl;
    }

    public void setMaxLvl(Integer maxLvl) {
        this.maxLvl = maxLvl;
    }
}