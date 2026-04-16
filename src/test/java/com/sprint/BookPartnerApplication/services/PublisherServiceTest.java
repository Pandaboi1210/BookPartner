package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.Publishers;
import com.sprint.BookPartnerApplication.repository.PublishersRepository;
import com.sprint.BookPartnerApplication.servicesImpl.PublisherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PublisherServiceTest {

    @Mock
    private PublishersRepository publishersRepository;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    private Publishers testPublisher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Test data - CREATE
        testPublisher = new Publishers();
        testPublisher.setPubId("0877");
        testPublisher.setPubName("Tech Publishing House");
        testPublisher.setCity("San Francisco");
        testPublisher.setState("CA");
        testPublisher.setCountry("USA");
    }

    @Test
    public void testCreatePublisher() {
        when(publishersRepository.save(testPublisher)).thenReturn(testPublisher);
        
        Publishers result = publisherService.createPublisher(testPublisher);
        
        assertNotNull(result);
        assertEquals("0877", result.getPubId());
        assertEquals("Tech Publishing House", result.getPubName());
        verify(publishersRepository, times(1)).save(testPublisher);
    }

    @Test
    public void testGetPublisherById() {
        when(publishersRepository.findById("0877")).thenReturn(java.util.Optional.of(testPublisher));
        
        Publishers result = publisherService.getPublisherById("0877");
        
        assertNotNull(result);
        assertEquals("0877", result.getPubId());
        verify(publishersRepository, times(1)).findById("0877");
    }

    @Test
    public void testGetAllPublishers() {
        List<Publishers> publishersList = new ArrayList<>();
        publishersList.add(testPublisher);
        
        when(publishersRepository.findAll()).thenReturn(publishersList);
        
        List<Publishers> result = publisherService.getAllPublishers();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(publishersRepository, times(1)).findAll();
    }

    @Test
    public void testUpdatePublisher() {
        Publishers updatedPublisher = new Publishers();
        updatedPublisher.setPubId("0877");
        updatedPublisher.setPubName("Global Tech Publishing");
        updatedPublisher.setCity("New York");
        updatedPublisher.setState("NY");
        updatedPublisher.setCountry("USA");

        when(publishersRepository.findById("0877")).thenReturn(java.util.Optional.of(testPublisher));
        when(publishersRepository.save(any(Publishers.class))).thenReturn(updatedPublisher);
        
        Publishers result = publisherService.updatePublisher("0877", updatedPublisher);
        
        assertNotNull(result);
        verify(publishersRepository, times(1)).findById("0877");
        verify(publishersRepository, times(1)).save(any(Publishers.class));
    }

    @Test
    public void testDeletePublisher() {
        doNothing().when(publishersRepository).deleteById("0877");
        
        publisherService.deletePublisher("0877");
        
        verify(publishersRepository, times(1)).deleteById("0877");
    }
}
