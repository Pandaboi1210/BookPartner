package com.sprint.BookPartnerApplication.servicesImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.BookPartnerApplication.entity.Jobs;
import com.sprint.BookPartnerApplication.exception.JobsException;
import com.sprint.BookPartnerApplication.repository.JobsRepository;
import com.sprint.BookPartnerApplication.services.JobsService;

@Service
public class JobServiceImpl implements JobsService {

    @Autowired
    private JobsRepository repo;

    @Override
    public Jobs createJob(Jobs job) {

        if (job.getMinLvl() < 10 || job.getMaxLvl() > 250) {
            throw new JobsException("Job level must be between 10 and 250");
        }

        return repo.save(job);
    }

    @Override
    public List<Jobs> getAllJobs() {
        return repo.findAll();
    }

    @Override
    public Jobs getJobById(Short jobId) {
        return repo.findById(jobId)
                .orElseThrow(() -> new JobsException("Job not found with id: " + jobId));
    }

    @Override
    public Jobs updateJob(Short jobId, Jobs job) {

        Jobs existing = getJobById(jobId);

        existing.setJobDesc(job.getJobDesc());
        existing.setMinLvl(job.getMinLvl());
        existing.setMaxLvl(job.getMaxLvl());

        return repo.save(existing);
    }
}