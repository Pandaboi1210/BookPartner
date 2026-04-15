package com.sprint.BookPartnerApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.BookPartnerApplication.entity.Jobs;
import com.sprint.BookPartnerApplication.repository.JobsRepository;

@Service
public class JobService {

    @Autowired
    private JobsRepository repo;

    // CREATE
    public Jobs save(Jobs job) {
        return repo.save(job);
    }

    // READ ONE (optional test)
    public Jobs getOne(Object id) {
        return repo.findById((Long) id).orElse(null);
    }
}