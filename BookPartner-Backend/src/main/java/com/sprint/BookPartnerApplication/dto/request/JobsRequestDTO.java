package com.sprint.BookPartnerApplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class JobsRequestDTO {

    @NotNull(message = "Job ID is required")
    private Short jobId;

    @NotBlank(message = "Job description is required")
    private String jobDesc;

    @NotNull(message = "Minimum level is required")
    private Integer minLvl;

    @NotNull(message = "Maximum level is required")
    private Integer maxLvl;

    //Getters & Setters

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