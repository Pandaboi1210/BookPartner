package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.RoyschedRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.RoyschedResponseDTO;
import com.sprint.BookPartnerApplication.entity.Roysched;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.exception.DuplicateResourceException;
import com.sprint.BookPartnerApplication.exception.InvalidInputException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
import com.sprint.BookPartnerApplication.repository.RoyschedRepository;
import com.sprint.BookPartnerApplication.repository.TitleRepository;
import com.sprint.BookPartnerApplication.servicesImpl.RoyschedServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoyschedServiceTest {

    @Mock
    private RoyschedRepository royschedRepository;

    @Mock
    private TitleRepository titleRepository;

    @InjectMocks
    private RoyschedServiceImpl royschedService;

    private RoyschedRequestDTO testRequestDTO;
    private Roysched testRoysched;
    private Title testTitle;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // 1. Setup Request DTO
        testRequestDTO = new RoyschedRequestDTO();
        testRequestDTO.setTitleId("TC1025");
        testRequestDTO.setLorange(1);
        testRequestDTO.setHirange(5000);
        testRequestDTO.setRoyalty(10);
        
        // 2. Setup Mock Title
        testTitle = new Title();
        testTitle.setTitleId("TC1025");
        testTitle.setTitle("The Busy Executive's Database Guide");

        // 3. Setup Mock Roysched Entity
        testRoysched = new Roysched();
        testRoysched.setRoyschedId(1);
        testRoysched.setTitle(testTitle);
        testRoysched.setLorange(1);
        testRoysched.setHirange(5000);
        testRoysched.setRoyalty(10);
    }

    // ==========================================
    // POSITIVE SCENARIOS (3 Tests)
    // ==========================================

    // TC 1
    @Test
    public void testCreateRoysched_Success() {
        when(titleRepository.findById("TC1025")).thenReturn(Optional.of(testTitle));
        when(royschedRepository.existsRoyschedRange(anyString(), anyInt(), anyInt())).thenReturn(false);
        when(royschedRepository.save(any(Roysched.class))).thenReturn(testRoysched);
        
        RoyschedResponseDTO result = royschedService.createRoysched(testRequestDTO);
        
        assertNotNull(result);
        assertEquals(10, result.getRoyalty());
        assertEquals(1, result.getLorange());
        assertEquals("TC1025", result.getTitleId());
        verify(royschedRepository, times(1)).save(any(Roysched.class));
    }

    // TC 2
    @Test
    public void testGetRoyschedByTitle_Success() {
        List<Roysched> royschedList = new ArrayList<>();
        royschedList.add(testRoysched);
        
        when(royschedRepository.findByTitle_TitleId("TC1025")).thenReturn(royschedList);
        
        List<RoyschedResponseDTO> result = royschedService.getRoyschedByTitle("TC1025");
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(10, result.get(0).getRoyalty());
        verify(royschedRepository, times(1)).findByTitle_TitleId("TC1025");
    }

    // TC 3
    @Test
    public void testUpdateRoysched_Success() {
        RoyschedRequestDTO updateDTO = new RoyschedRequestDTO();
        updateDTO.setTitleId("TC1025");
        updateDTO.setLorange(1);
        updateDTO.setHirange(10000);
        updateDTO.setRoyalty(12);
        
        Roysched updatedRoysched = new Roysched();
        updatedRoysched.setRoyschedId(1);
        updatedRoysched.setTitle(testTitle);
        updatedRoysched.setLorange(1);
        updatedRoysched.setHirange(10000);
        updatedRoysched.setRoyalty(12);
        
        when(royschedRepository.findById(1)).thenReturn(Optional.of(testRoysched));
        when(titleRepository.findById("TC1025")).thenReturn(Optional.of(testTitle));
        when(royschedRepository.save(any(Roysched.class))).thenReturn(updatedRoysched);
        
        RoyschedResponseDTO result = royschedService.updateRoysched(1, updateDTO);
        
        assertNotNull(result);
        assertEquals(12, result.getRoyalty());
        assertEquals(10000, result.getHirange());
        verify(royschedRepository, times(1)).findById(1);
        verify(royschedRepository, times(1)).save(any(Roysched.class));
    }

    // ==========================================
    // NEGATIVE SCENARIOS (5 Tests)
    // ==========================================

    // TC 4: Attempting to create a schedule for a book that doesn't exist
    @Test
    public void testCreateRoysched_TitleNotFound() {
        when(titleRepository.findById("TC1025")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            royschedService.createRoysched(testRequestDTO);
        });

        // Ensure we never hit the database save method
        verify(royschedRepository, never()).save(any(Roysched.class));
    }

    // TC 5: Attempting to create a schedule where Low Range > High Range
    @Test
    public void testCreateRoysched_InvalidRange() {
        when(titleRepository.findById("TC1025")).thenReturn(Optional.of(testTitle));
        
        // Tamper with the DTO to make low range higher than high range
        testRequestDTO.setLorange(10000);
        testRequestDTO.setHirange(5000);

        assertThrows(InvalidInputException.class, () -> {
            royschedService.createRoysched(testRequestDTO);
        });

        verify(royschedRepository, never()).save(any(Roysched.class));
    }

    // TC 6: Attempting to create a duplicate exact range for the same book
    @Test
    public void testCreateRoysched_DuplicateRange() {
        when(titleRepository.findById("TC1025")).thenReturn(Optional.of(testTitle));
        
        // Mock the custom query to return true (duplicate found)
        when(royschedRepository.existsRoyschedRange("TC1025", 1, 5000)).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> {
            royschedService.createRoysched(testRequestDTO);
        });

        verify(royschedRepository, never()).save(any(Roysched.class));
    }

    // TC 7: Fetching royalty schedules for a book that has none
    @Test
    public void testGetRoyschedByTitle_EmptyList() {
        // Return an empty list instead of data
        when(royschedRepository.findByTitle_TitleId("UNKNOWN_TITLE")).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            royschedService.getRoyschedByTitle("UNKNOWN_TITLE");
        });
    }

    // TC 8: Attempting to update a schedule ID that doesn't exist
    @Test
    public void testUpdateRoysched_NotFound() {
        when(royschedRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            royschedService.updateRoysched(999, testRequestDTO);
        });

        verify(royschedRepository, never()).save(any(Roysched.class));
    }
}