package com.sprint.BookPartnerApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.BookPartnerApplication.entity.Jobs;
import com.sprint.BookPartnerApplication.repository.JobsRepository;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobsRepository repo;

    public Jobs save(Jobs job) {
        return repo.save(job);
    }

    public List<Jobs> getAll() {
        return repo.findAll();
    }

    public Jobs getOne(Short id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Short id) {
        repo.deleteById(id);
    }
}