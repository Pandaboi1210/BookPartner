package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.JobsRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.JobsResponseDTO;
import com.sprint.BookPartnerApplication.services.JobsService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    private final JobsService service;

    public JobController(JobsService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<JobsResponseDTO> createJob(
            @Valid @RequestBody JobsRequestDTO requestDTO) {

        JobsResponseDTO data = service.createJob(requestDTO);

        return created(data);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<JobsResponseDTO>> getAllJobs() {

        List<JobsResponseDTO> data = service.getAllJobs();

        return ok(data);
    }

    // GET BY ID
    @GetMapping("/{jobId}")
    public ResponseEntity<JobsResponseDTO> getJobById(
            @PathVariable Short jobId) {

        JobsResponseDTO data = service.getJobById(jobId);

        return ok(data);
    }

    // UPDATE
    @PutMapping("/{jobId}")
    public ResponseEntity<JobsResponseDTO> updateJob(
            @PathVariable Short jobId,
            @Valid @RequestBody JobsRequestDTO requestDTO) {

        JobsResponseDTO data =
                service.updateJob(jobId, requestDTO);

        return ok(data);
    }

    //Helper methods
    private <T> ResponseEntity<T> ok(T data) {
        return ResponseEntity.ok(data);
    }

    private <T> ResponseEntity<T> created(T data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(data);
    }
}