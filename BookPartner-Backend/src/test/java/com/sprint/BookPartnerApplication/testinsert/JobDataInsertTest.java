package com.sprint.BookPartnerApplication.testinsert;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sprint.BookPartnerApplication.dto.request.JobsRequestDTO;
import com.sprint.BookPartnerApplication.repository.JobsRepository;
import com.sprint.BookPartnerApplication.services.JobsService;

@SpringBootTest
@Order(1)
public class JobDataInsertTest {

    @Autowired private JobsService jobsService;
    @Autowired private JobsRepository jobsRepository;

    @FunctionalInterface
    interface InsertAction { void execute(); }

    private void safeInsert(InsertAction action) {
        try { action.execute(); } catch (Exception e) { /* Already exists, skip */ }
    }

    private void insertJob(short jobId, String desc, int minLvl, int maxLvl) {
        if (jobsRepository.existsById(jobId)) return;
        safeInsert(() -> {
            JobsRequestDTO dto = new JobsRequestDTO();
            dto.setJobId(jobId);
            dto.setJobDesc(desc);
            dto.setMinLvl(minLvl);
            dto.setMaxLvl(maxLvl);
            jobsService.createJob(dto);
        });
    }

    @Test
    public void insertJobs() {
        if (jobsRepository.count() >= 10) {
            System.out.println("Already have " + jobsRepository.count() + " jobs. Skipping insertion.");
            return;
        }

        insertJob((short)  1, "New Hire - Job not specified",  10,  10);
        insertJob((short)  2, "Chief Executive Officer",      200, 250);
        insertJob((short)  3, "Business Operations Manager",  175, 225);
        insertJob((short)  4, "Chief Financial Officier",     175, 250);
        insertJob((short)  5, "Publisher",                    150, 250);
        insertJob((short)  6, "Managing Editor",              140, 225);
        insertJob((short)  7, "Marketing Manager",            120, 200);
        insertJob((short)  8, "Public Relations Manager",     100, 175);
        insertJob((short)  9, "Acquisitions Manager",          75, 175);
        insertJob((short) 10, "Productions Manager",           75, 165);
        insertJob((short) 11, "Operations Manager",            75, 150);
        insertJob((short) 12, "Editor",                        25, 100);
        insertJob((short) 13, "Sales Representative",          25, 100);
        insertJob((short) 14, "Designer",                      25, 100);
        System.out.println("Jobs insertion completed. Total count: " + jobsRepository.count());
    }
}
