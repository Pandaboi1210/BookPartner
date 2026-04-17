package com.sprint.BookPartnerApplication.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.BookPartnerApplication.entity.Jobs;
import com.sprint.BookPartnerApplication.exception.DuplicateResourceException;
import com.sprint.BookPartnerApplication.exception.InvalidInputException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
import com.sprint.BookPartnerApplication.repository.JobsRepository;
import com.sprint.BookPartnerApplication.services.JobsService;

@Service
public class JobServiceImpl implements JobsService {

    @Autowired
    private JobsRepository repo;

    @Override
    public Jobs createJob(Jobs job) {
        
        // 🚨 400 BAD REQUEST: Business rule validation
        if (job.getMinLvl() < 10 || job.getMaxLvl() > 250) {
            throw new InvalidInputException("Job level must be between 10 and 250");
        }

        // 🚨 409 CONFLICT: Check if the job description already exists!
        if (repo.existsByJobDesc(job.getJobDesc())) {
            throw new DuplicateResourceException("A job with the description '" + job.getJobDesc() + "' already exists.");
        }

        return repo.save(job);
    }
    @Override
    public List<Jobs> getAllJobs() {
        return repo.findAll();
    }

    @Override
    public Jobs getJobById(Short jobId) {
        // 🚨 404 NOT FOUND: Database lookup failure
        return repo.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));
    }

    @Override
    public Jobs updateJob(Short jobId, Jobs job) {

        // This will automatically throw ResourceNotFoundException if missing!
        Jobs existing = getJobById(jobId);

        // 🚨 400 BAD REQUEST: We must re-validate the levels during an update too!
        if (job.getMinLvl() < 10 || job.getMaxLvl() > 250) {
            throw new InvalidInputException("Job level must be between 10 and 250");
        }

        existing.setJobDesc(job.getJobDesc());
        existing.setMinLvl(job.getMinLvl());
        existing.setMaxLvl(job.getMaxLvl());

        return repo.save(existing);
    }
}