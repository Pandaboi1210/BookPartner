package com.sprint.BookPartnerApplication.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "jobs")
public class Jobs {

    @Id
    @Column(name = "job_id")
    private Short jobId;

    @Column(name = "job_desc")
    private String jobDesc;

    @Column(name = "min_lvl")
    private int minLvl;

    @Column(name = "max_lvl")
    private int maxLvl;

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

    public int getMinLvl() {
        return minLvl;
    }

    public void setMinLvl(int minLvl) {
        this.minLvl = minLvl;
    }

    public int getMaxLvl() {
        return maxLvl;
    }

    public void setMaxLvl(int maxLvl) {
        this.maxLvl = maxLvl;
    }
}