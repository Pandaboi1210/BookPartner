package com.sprint.BookPartnerApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.sprint.BookPartnerApplication.dto.request.JobsRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.JobsResponseDTO;
import com.sprint.BookPartnerApplication.services.JobsService;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    @Autowired
    private JobsService service;

    //CREATE
    @PostMapping
    public JobsResponseDTO create(
            @Valid @RequestBody JobsRequestDTO dto) {

        return service.createJob(dto);
    }

    // GET ALL
    @GetMapping
    public List<JobsResponseDTO> getAll() {
        return service.getAllJobs();
    }

    // GET BY ID
    @GetMapping("/{jobId}")
    public JobsResponseDTO getById(@PathVariable Short jobId) {
        return service.getJobById(jobId);
    }

    // UPDATE
    @PutMapping("/{jobId}")
    public JobsResponseDTO update(
            @PathVariable Short jobId,
            @Valid @RequestBody JobsRequestDTO dto) {

        return service.updateJob(jobId, dto);
    }
}