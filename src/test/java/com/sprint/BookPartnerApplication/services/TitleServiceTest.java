package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.Title;
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

    private Title testTitle;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Test data - CREATE (INSERT)
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

    @Test
    public void testInsertTitle() {
        when(titleRepository.save(testTitle)).thenReturn(testTitle);
        
        Title result = titleService.insertTitle(testTitle);
        
        assertNotNull(result);
        assertEquals("TC1025", result.getTitleId());
        assertEquals("The Busy Executive's Database Guide", result.getTitle());
        verify(titleRepository, times(1)).save(testTitle);
    }

    @Test
    public void testGetTitleById() {
        when(titleRepository.findById("TC1025")).thenReturn(Optional.of(testTitle));
        
        Optional<Title> result = titleService.getTitleById("TC1025");
        
        assertTrue(result.isPresent());
        assertEquals("TC1025", result.get().getTitleId());
        verify(titleRepository, times(1)).findById("TC1025");
    }

    @Test
    public void testGetAllTitles() {
        List<Title> titleList = new ArrayList<>();
        titleList.add(testTitle);
        
        when(titleRepository.findAll()).thenReturn(titleList);
        
        List<Title> result = titleService.getAllTitles();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(titleRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateTitleById() {
        Title updatedTitle = new Title();
        updatedTitle.setTitleId("TC1025");
        updatedTitle.setTitle("Advanced Executive's Database Guide");
        updatedTitle.setPrice(29.99);
        updatedTitle.setRoyalty(12);

        when(titleRepository.findById("TC1025")).thenReturn(Optional.of(testTitle));
        when(titleRepository.save(any(Title.class))).thenReturn(updatedTitle);
        
        Title result = titleService.updateTitleById("TC1025", updatedTitle);
        
        assertNotNull(result);
        verify(titleRepository, times(1)).findById("TC1025");
        verify(titleRepository, times(1)).save(any(Title.class));
    }

    @Test
    public void testGetTitlesByType() {
        List<Title> titleList = new ArrayList<>();
        titleList.add(testTitle);
        
        when(titleRepository.findByType("Business")).thenReturn(titleList);
        
        List<Title> result = titleService.getTitlesByType("Business");
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(titleRepository, times(1)).findByType("Business");
    }
}
