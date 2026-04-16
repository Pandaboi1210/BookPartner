package com.sprint.BookPartnerApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sprint.BookPartnerApplication.entity.Jobs;
import com.sprint.BookPartnerApplication.services.JobService;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService service;

    @PostMapping
    public Jobs save(@RequestBody Jobs job) {
        return service.save(job);
    }

    @GetMapping
    public List<Jobs> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Jobs getOne(@PathVariable Short id) {
        return service.getOne(id);
    }

    @PutMapping("/{id}")
    public Jobs update(@PathVariable Short id, @RequestBody Jobs job) {
        job.setJobId(id);
        return service.save(job);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Short id) {
        service.delete(id);
        return "Job deleted successfully!";
    }
}