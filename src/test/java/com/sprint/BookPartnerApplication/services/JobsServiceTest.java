package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.JobsRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.JobsResponseDTO;
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

    private JobsRequestDTO testJobRequestDTO;
    private JobsResponseDTO testJobResponseDTO;
    private Jobs testJob;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Test data - CREATE
        testJobRequestDTO = new JobsRequestDTO();
        testJobRequestDTO.setJobId((short) 1);
        testJobRequestDTO.setJobDesc("Software Engineer");
        testJobRequestDTO.setMinLvl(10);
        testJobRequestDTO.setMaxLvl(50);
        
        testJobResponseDTO = new JobsResponseDTO();
        testJobResponseDTO.setJobId((short) 1);
        testJobResponseDTO.setJobDesc("Software Engineer");
        testJobResponseDTO.setMinLvl(10);
        testJobResponseDTO.setMaxLvl(50);
        
        testJob = new Jobs();
        testJob.setJobId((short) 1);
        testJob.setJobDesc("Software Engineer");
        testJob.setMinLvl(10);
        testJob.setMaxLvl(50);
    }

    @Test
    public void testCreateJob() {
        when(jobsRepository.save(any(Jobs.class))).thenReturn(testJob);
        
        JobsResponseDTO result = jobsService.createJob(testJobRequestDTO);
        
        assertNotNull(result);
        assertEquals("Software Engineer", result.getJobDesc());
        assertEquals(10, result.getMinLvl());
        assertEquals(50, result.getMaxLvl());
        verify(jobsRepository, times(1)).save(any(Jobs.class));
    }

    @Test
    public void testGetJobById() {
        when(jobsRepository.findById((short) 1)).thenReturn(java.util.Optional.of(testJob));
        
        JobsResponseDTO result = jobsService.getJobById((short) 1);
        
        assertNotNull(result);
        assertEquals("Software Engineer", result.getJobDesc());
        verify(jobsRepository, times(1)).findById((short) 1);
    }

    @Test
    public void testGetAllJobs() {
        List<Jobs> jobsList = new ArrayList<>();
        jobsList.add(testJob);
        
        when(jobsRepository.findAll()).thenReturn(jobsList);
        
        List<JobsResponseDTO> result = jobsService.getAllJobs();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(jobsRepository, times(1)).findAll();
    }
}
