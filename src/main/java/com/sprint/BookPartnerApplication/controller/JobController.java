package com.sprint.BookPartnerApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sprint.BookPartnerApplication.entity.Jobs;
import com.sprint.BookPartnerApplication.services.JobsService;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    @Autowired
    private JobsService service;

    // POST 
    @PostMapping
    public Jobs create(@RequestBody Jobs job) {
        return service.createJob(job);
    }

    // GET 
    @GetMapping
    public List<Jobs> getAll() {
        return service.getAllJobs();
    }

    // GET 
    @GetMapping("/{jobId}")
    public Jobs getById(@PathVariable Short jobId) {
        return service.getJobById(jobId);
    }
}