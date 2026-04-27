package com.sprint.BookPartnerApplication.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.BookPartnerApplication.dto.request.JobsRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.JobsResponseDTO;
import com.sprint.BookPartnerApplication.entity.Jobs;
import com.sprint.BookPartnerApplication.exception.JobsException;
import com.sprint.BookPartnerApplication.repository.JobsRepository;
import com.sprint.BookPartnerApplication.services.JobsService;

@Service
public class JobServiceImpl implements JobsService {

    @Autowired
    private JobsRepository repo;

    // 🔥 CREATE
    @Override
    public JobsResponseDTO createJob(JobsRequestDTO dto) {

        if (dto.getMinLvl() < 10 || dto.getMaxLvl() > 250) {
            throw new JobsException("Job level must be between 10 and 250");
        }

        if (dto.getMinLvl() > dto.getMaxLvl()) {
            throw new JobsException("Minimum level cannot be greater than maximum level");
        }

        Jobs job = new Jobs();
        job.setJobId(dto.getJobId());
        job.setJobDesc(dto.getJobDesc());
        job.setMinLvl(dto.getMinLvl());
        job.setMaxLvl(dto.getMaxLvl());

        repo.save(job);

        return mapToResponse(job);
    }

    // 🔥 GET ALL
    @Override
    public List<JobsResponseDTO> getAllJobs() {
        return repo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 🔥 GET BY ID
    @Override
    public JobsResponseDTO getJobById(Short jobId) {

        Jobs job = repo.findById(jobId)
                .orElseThrow(() -> new JobsException("Job not found with id: " + jobId));

        return mapToResponse(job);
    }

    // 🔥 UPDATE
    @Override
    public JobsResponseDTO updateJob(Short jobId, JobsRequestDTO dto) {

        Jobs existing = repo.findById(jobId)
                .orElseThrow(() -> new JobsException("Job not found"));

        existing.setJobDesc(dto.getJobDesc());

        if (dto.getMinLvl() < 10 || dto.getMaxLvl() > 250) {
            throw new JobsException("Job level must be between 10 and 250");
        }
        if (dto.getMinLvl() > dto.getMaxLvl()) {
            throw new JobsException("Minimum level cannot be greater than maximum level");
        }

        existing.setMinLvl(dto.getMinLvl());
        existing.setMaxLvl(dto.getMaxLvl());

        repo.save(existing);

        return mapToResponse(existing);
    }

    // 🔥 COMMON MAPPING
    private JobsResponseDTO mapToResponse(Jobs job) {

        JobsResponseDTO res = new JobsResponseDTO();

        res.setJobId(job.getJobId());
        res.setJobDesc(job.getJobDesc());
        res.setMinLvl(job.getMinLvl());
        res.setMaxLvl(job.getMaxLvl());

        return res;
    }
}