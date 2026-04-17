package com.sprint.BookPartnerApplication.services;

import java.util.List;
import com.sprint.BookPartnerApplication.entity.Jobs;

public interface JobsService {

    Jobs createJob(Jobs job);

    List<Jobs> getAllJobs();

    Jobs getJobById(Short jobId);
    
    Jobs updateJob(Short jobId, Jobs job);
}