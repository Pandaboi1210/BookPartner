package com.sprint.BookPartnerApplication.services;

import java.util.List;

import com.sprint.BookPartnerApplication.dto.request.JobsRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.JobsResponseDTO;

public interface JobsService {

    JobsResponseDTO createJob(JobsRequestDTO dto);

    List<JobsResponseDTO> getAllJobs();

    JobsResponseDTO getJobById(Short jobId);

    JobsResponseDTO updateJob(Short jobId, JobsRequestDTO dto);
}