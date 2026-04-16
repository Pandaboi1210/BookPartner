package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.Jobs;
import com.sprint.BookPartnerApplication.repository.JobsRepository;
import com.sprint.BookPartnerApplication.servicesImpl.JobServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JobsServiceTest {

    @Mock
    private JobsRepository jobsRepository;

    @InjectMocks
    private JobServiceImpl jobsService;

    private Jobs testJob;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Test data - CREATE
        testJob = new Jobs();
        testJob.setJobId((short) 1);
        testJob.setJobDesc("Software Engineer");
        testJob.setMinLvl(5);
        testJob.setMaxLvl(10);
    }

    @Test
    public void testCreateJob() {
        when(jobsRepository.save(testJob)).thenReturn(testJob);
        
        Jobs result = jobsService.createJob(testJob);
        
        assertNotNull(result);
        assertEquals("Software Engineer", result.getJobDesc());
        assertEquals(5, result.getMinLvl());
        verify(jobsRepository, times(1)).save(testJob);
    }

    @Test
    public void testGetJobById() {
        when(jobsRepository.findById((short) 1)).thenReturn(java.util.Optional.of(testJob));
        
        Jobs result = jobsService.getJobById((short) 1);
        
        assertNotNull(result);
        assertEquals("Software Engineer", result.getJobDesc());
        verify(jobsRepository, times(1)).findById((short) 1);
    }

    @Test
    public void testGetAllJobs() {
        List<Jobs> jobsList = new ArrayList<>();
        jobsList.add(testJob);
        
        when(jobsRepository.findAll()).thenReturn(jobsList);
        
        List<Jobs> result = jobsService.getAllJobs();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(jobsRepository, times(1)).findAll();
    }
}
