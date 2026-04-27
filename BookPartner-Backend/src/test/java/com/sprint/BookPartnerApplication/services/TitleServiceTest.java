package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.TitleRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.TitleResponseDTO;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.exception.DuplicateResourceException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
import com.sprint.BookPartnerApplication.repository.TitleRepository;
import com.sprint.BookPartnerApplication.servicesImpl.TitleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TitleServiceTest {

    @Mock
    private TitleRepository titleRepository;

    @InjectMocks
    private TitleServiceImpl titleService;

    private TitleRequestDTO testTitleRequestDTO;
    private TitleResponseDTO testTitleResponseDTO;
    private Title testTitle;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Test data - CREATE (INSERT) - RequestDTO
        testTitleRequestDTO = new TitleRequestDTO();
        testTitleRequestDTO.setTitleId("TC1025");
        testTitleRequestDTO.setTitle("The Busy Executive's Database Guide");
        testTitleRequestDTO.setType("Business");
        testTitleRequestDTO.setPubdate(LocalDateTime.now());
        testTitleRequestDTO.setPrice(19.99);
        testTitleRequestDTO.setAdvance(5000.00);
        testTitleRequestDTO.setRoyalty(10);
        testTitleRequestDTO.setYtdSales(4095);
        testTitleRequestDTO.setNotes("An essential reference");
        
        // Test data - Response DTO
        testTitleResponseDTO = new TitleResponseDTO();
        testTitleResponseDTO.setTitleId("TC1025");
        testTitleResponseDTO.setTitle("The Busy Executive's Database Guide");
        testTitleResponseDTO.setType("Business");
        testTitleResponseDTO.setPubdate(LocalDateTime.now());
        testTitleResponseDTO.setPrice(19.99);
        testTitleResponseDTO.setAdvance(5000.00);
        testTitleResponseDTO.setRoyalty(10);
        testTitleResponseDTO.setYtdSales(4095);
        testTitleResponseDTO.setNotes("An essential reference");
        
        // Test data - Entity (for mocking repository)
        testTitle = new Title();
        testTitle.setTitleId("TC1025");
        testTitle.setTitle("The Busy Executive's Database Guide");
        testTitle.setType("Business");
        testTitle.setPubdate(LocalDateTime.now());
        testTitle.setPrice(19.99);
        testTitle.setAdvance(5000.00);
        testTitle.setRoyalty(10);
        testTitle.setYtdSales(4095);
        testTitle.setNotes("An essential reference");
    }

    // --- POSITIVE TEST CASES ---

    // TC 1
    @Test
    public void testInsertTitle() {
        when(titleRepository.save(any(Title.class))).thenReturn(testTitle);
        
        TitleResponseDTO result = titleService.insertTitle(testTitleRequestDTO);
        
        assertNotNull(result);
        assertEquals("TC1025", result.getTitleId());
        assertEquals("The Busy Executive's Database Guide", result.getTitle());
        verify(titleRepository, times(1)).save(any(Title.class));
    }

    // TC 2
    @Test
    public void testGetTitleById() {
        when(titleRepository.findById("TC1025")).thenReturn(Optional.of(testTitle));
        
        TitleResponseDTO result = titleService.getTitleById("TC1025");
        
        assertNotNull(result);
        assertEquals("TC1025", result.getTitleId());
        verify(titleRepository, times(1)).findById("TC1025");
    }

    // TC 3
    @Test
    public void testGetAllTitles() {
        List<Title> titleList = new ArrayList<>();
        titleList.add(testTitle);
        
        when(titleRepository.findAll()).thenReturn(titleList);
        
        List<TitleResponseDTO> result = titleService.getAllTitles();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(titleRepository, times(1)).findAll();
    }

    // TC 4
    @Test
    public void testUpdateTitleById() {
        TitleRequestDTO updatedTitleDTO = new TitleRequestDTO();
        updatedTitleDTO.setTitleId("TC1025");
        updatedTitleDTO.setTitle("Advanced Executive's Database Guide");
        updatedTitleDTO.setType("Business");
        updatedTitleDTO.setPrice(29.99);
        updatedTitleDTO.setRoyalty(12);
        updatedTitleDTO.setPubdate(LocalDateTime.now());

        Title updatedTitle = new Title();
        updatedTitle.setTitleId("TC1025");
        updatedTitle.setTitle("Advanced Executive's Database Guide");
        updatedTitle.setType("Business");
        updatedTitle.setPrice(29.99);
        updatedTitle.setRoyalty(12);
        updatedTitle.setPubdate(LocalDateTime.now());

        when(titleRepository.findById("TC1025")).thenReturn(Optional.of(testTitle));
        when(titleRepository.save(any(Title.class))).thenReturn(updatedTitle);
        
        TitleResponseDTO result = titleService.updateTitleById("TC1025", updatedTitleDTO);
        
        assertNotNull(result);
        verify(titleRepository, times(1)).findById("TC1025");
        verify(titleRepository, times(1)).save(any(Title.class));
    }

    // TC 5
    @Test
    public void testGetTitlesByType() {
        List<Title> titleList = new ArrayList<>();
        titleList.add(testTitle);
        
        when(titleRepository.findByType("Business")).thenReturn(titleList);
        
        List<TitleResponseDTO> result = titleService.getTitlesByType("Business");
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(titleRepository, times(1)).findByType("Business");
    }

    // --- NEGATIVE TEST CASES ---

    // TC 6: Fetching a book ID that doesn't exist
    @Test
    public void testGetTitleById_NotFound() {
        when(titleRepository.findById("INVALID_ID")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            titleService.getTitleById("INVALID_ID");
        });

        verify(titleRepository, times(1)).findById("INVALID_ID");
    }

    // TC 7: Attempting to insert a book with an ID that is already in use
    @Test
    public void testInsertTitle_DuplicateResource() {
        when(titleRepository.existsById(testTitleRequestDTO.getTitleId())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> {
            titleService.insertTitle(testTitleRequestDTO);
        });

        // Proves that the service aborted before calling the database save method
        verify(titleRepository, never()).save(any(Title.class));
    }

    // TC 8: Attempting to delete a book ID that doesn't exist
    @Test
    public void testDeleteTitleById_NotFound() {
        when(titleRepository.existsById("INVALID_ID")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            titleService.deleteTitleById("INVALID_ID");
        });

        // Proves that the service aborted before calling the database delete method
        verify(titleRepository, never()).deleteById(anyString());
    }
}