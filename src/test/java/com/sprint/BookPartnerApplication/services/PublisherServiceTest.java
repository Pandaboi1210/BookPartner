package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.PublishersRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.PublishersResponseDTO;
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

    private PublishersRequestDTO testPublisherRequestDTO;
    private PublishersResponseDTO testPublisherResponseDTO;
    private Publishers testPublisher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Test data - CREATE (RequestDTO)
        testPublisherRequestDTO = new PublishersRequestDTO();
        testPublisherRequestDTO.setPubId("0877");
        testPublisherRequestDTO.setPubName("Tech Publishing House");
        testPublisherRequestDTO.setCity("San Francisco");
        testPublisherRequestDTO.setState("CA");
        testPublisherRequestDTO.setCountry("USA");
        
        // Test data - Response DTO
        testPublisherResponseDTO = new PublishersResponseDTO();
        testPublisherResponseDTO.setPubId("0877");
        testPublisherResponseDTO.setPubName("Tech Publishing House");
        testPublisherResponseDTO.setCity("San Francisco");
        testPublisherResponseDTO.setState("CA");
        testPublisherResponseDTO.setCountry("USA");
        
        // Test data - Entity (for mocking repository)
        testPublisher = new Publishers();
        testPublisher.setPubId("0877");
        testPublisher.setPubName("Tech Publishing House");
        testPublisher.setCity("San Francisco");
        testPublisher.setState("CA");
        testPublisher.setCountry("USA");
    }

    @Test
    public void testCreatePublisher() {
        when(publishersRepository.save(any(Publishers.class))).thenReturn(testPublisher);
        
        PublishersResponseDTO result = publisherService.createPublisher(testPublisherRequestDTO);
        
        assertNotNull(result);
        assertEquals("0877", result.getPubId());
        assertEquals("Tech Publishing House", result.getPubName());
        verify(publishersRepository, times(1)).save(any(Publishers.class));
    }

    @Test
    public void testGetPublisherById() {
        when(publishersRepository.findById("0877")).thenReturn(java.util.Optional.of(testPublisher));
        
        PublishersResponseDTO result = publisherService.getPublisherById("0877");
        
        assertNotNull(result);
        assertEquals("0877", result.getPubId());
        verify(publishersRepository, times(1)).findById("0877");
    }

    @Test
    public void testGetAllPublishers() {
        List<Publishers> publishersList = new ArrayList<>();
        publishersList.add(testPublisher);
        
        when(publishersRepository.findAll()).thenReturn(publishersList);
        
        List<PublishersResponseDTO> result = publisherService.getAllPublishers();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(publishersRepository, times(1)).findAll();
    }

    @Test
    public void testUpdatePublisher() {
        PublishersRequestDTO updatedPublisherDTO = new PublishersRequestDTO();
        updatedPublisherDTO.setPubId("0877");
        updatedPublisherDTO.setPubName("Global Tech Publishing");
        updatedPublisherDTO.setCity("New York");
        updatedPublisherDTO.setState("NY");
        updatedPublisherDTO.setCountry("USA");

        Publishers updatedPublisher = new Publishers();
        updatedPublisher.setPubId("0877");
        updatedPublisher.setPubName("Global Tech Publishing");
        updatedPublisher.setCity("New York");
        updatedPublisher.setState("NY");
        updatedPublisher.setCountry("USA");

        when(publishersRepository.findById("0877")).thenReturn(java.util.Optional.of(testPublisher));
        when(publishersRepository.save(any(Publishers.class))).thenReturn(updatedPublisher);
        
        PublishersResponseDTO result = publisherService.updatePublisher("0877", updatedPublisherDTO);
        
        assertNotNull(result);
        verify(publishersRepository, times(1)).findById("0877");
        verify(publishersRepository, times(1)).save(any(Publishers.class));
    }

    @Test
    public void testDeletePublisher() {
        when(publishersRepository.findById("0877")).thenReturn(java.util.Optional.of(testPublisher));
        when(publishersRepository.getEmployeesByPublisherId("0877")).thenReturn(new java.util.ArrayList<>());
        when(publishersRepository.getTitlesByPublisherId("0877")).thenReturn(new java.util.ArrayList<>());
        doNothing().when(publishersRepository).delete(testPublisher);
        
        publisherService.deletePublisher("0877");
        
        verify(publishersRepository, times(1)).findById("0877");
        verify(publishersRepository, times(1)).delete(testPublisher);
    }
}
