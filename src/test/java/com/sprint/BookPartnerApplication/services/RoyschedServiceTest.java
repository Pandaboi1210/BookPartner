package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.RoyschedRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.RoyschedResponseDTO;
import com.sprint.BookPartnerApplication.entity.Roysched;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.repository.RoyschedRepository;
import com.sprint.BookPartnerApplication.repository.TitleRepository;
import com.sprint.BookPartnerApplication.servicesImpl.RoyschedServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoyschedServiceTest {

    @Mock
    private RoyschedRepository royschedRepository;

    @Mock
    private TitleRepository titleRepository;

    @InjectMocks
    private RoyschedServiceImpl royschedService;

    private RoyschedRequestDTO testRoyschedRequestDTO;
    private RoyschedResponseDTO testRoyschedResponseDTO;
    private Roysched testRoysched;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Test data - CREATE (RequestDTO)
        testRoyschedRequestDTO = new RoyschedRequestDTO();
        testRoyschedRequestDTO.setTitleId("TC1025");
        testRoyschedRequestDTO.setLorange(1);
        testRoyschedRequestDTO.setHirange(5000);
        testRoyschedRequestDTO.setRoyalty(10);
        
        // Test data - Response DTO
        testRoyschedResponseDTO = new RoyschedResponseDTO();
        testRoyschedResponseDTO.setTitleId("TC1025");
        testRoyschedResponseDTO.setLorange(1);
        testRoyschedResponseDTO.setHirange(5000);
        testRoyschedResponseDTO.setRoyalty(10);
        
        // Test data - Entity (for mocking repository)
        testRoysched = new Roysched();
        testRoysched.setRoyschedId(1);
        testRoysched.setLorange(1);
        testRoysched.setHirange(5000);
        testRoysched.setRoyalty(10);
    }

    @Test
    public void testCreateRoysched() {
        Title title = new Title();
        title.setTitleId("TC1025");
        when(titleRepository.findById("TC1025")).thenReturn(java.util.Optional.of(title));
        when(royschedRepository.save(any(Roysched.class))).thenReturn(testRoysched);
        
        RoyschedResponseDTO result = royschedService.createRoysched(testRoyschedRequestDTO);
        
        assertNotNull(result);
        assertEquals(10, result.getRoyalty());
        assertEquals(1, result.getLorange());
        verify(royschedRepository, times(1)).save(any(Roysched.class));
    }

    @Test
    public void testGetRoyschedByTitle() {
        List<Roysched> royschedList = new ArrayList<>();
        royschedList.add(testRoysched);
        
        when(royschedRepository.findByTitle_TitleId("TC1025")).thenReturn(royschedList);
        
        List<RoyschedResponseDTO> result = royschedService.getRoyschedByTitle("TC1025");
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(royschedRepository, times(1)).findByTitle_TitleId("TC1025");
    }

    @Test
    public void testUpdateRoysched() {
        RoyschedRequestDTO updatedRoyschedDTO = new RoyschedRequestDTO();
        updatedRoyschedDTO.setTitleId("TC1025");
        updatedRoyschedDTO.setLorange(1);
        updatedRoyschedDTO.setHirange(10000);
        updatedRoyschedDTO.setRoyalty(12);
        
        Roysched updatedRoysched = new Roysched();
        updatedRoysched.setRoyschedId(1);
        updatedRoysched.setLorange(1);
        updatedRoysched.setHirange(10000);
        updatedRoysched.setRoyalty(12);
        
        Title title = new Title();
        title.setTitleId("TC1025");
        when(titleRepository.findById("TC1025")).thenReturn(java.util.Optional.of(title));
        when(royschedRepository.findById(1)).thenReturn(java.util.Optional.of(testRoysched));
        when(royschedRepository.save(any(Roysched.class))).thenReturn(updatedRoysched);
        
        RoyschedResponseDTO result = royschedService.updateRoysched(1, updatedRoyschedDTO);
        
        assertNotNull(result);
        verify(royschedRepository, times(1)).findById(1);
        verify(royschedRepository, times(1)).save(any(Roysched.class));
    }
}
