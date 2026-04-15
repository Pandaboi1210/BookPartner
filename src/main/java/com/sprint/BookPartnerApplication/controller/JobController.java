package com.sprint.BookPartnerApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sprint.BookPartnerApplication.entity.Jobs;
import com.sprint.BookPartnerApplication.services.JobService;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService service;

    // CREATE JOB
    @PostMapping
    public Jobs save(@RequestBody Jobs job) {
        return service.save(job);
    }

    // GET ONE JOB (optional)
    @GetMapping("/{id}")
    public Jobs getOne(@PathVariable Short id) {
        return service.getOne(id);
    }
}