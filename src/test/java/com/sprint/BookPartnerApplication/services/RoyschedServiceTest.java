package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.Roysched;
import com.sprint.BookPartnerApplication.repository.RoyschedRepository;
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

    @InjectMocks
    private RoyschedServiceImpl royschedService;

    private Roysched testRoysched;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Test data - CREATE
        testRoysched = new Roysched();
        testRoysched.setRoyschedId(1);
        testRoysched.setLorange(1);
        testRoysched.setHirange(5000);
        testRoysched.setRoyalty(10);
    }

    @Test
    public void testCreateRoysched() {
        when(royschedRepository.save(testRoysched)).thenReturn(testRoysched);
        
        Roysched result = royschedService.createRoysched(testRoysched);
        
        assertNotNull(result);
        assertEquals(10, result.getRoyalty());
        assertEquals(1, result.getLorange());
        verify(royschedRepository, times(1)).save(testRoysched);
    }

    @Test
    public void testGetRoyschedByTitle() {
        List<Roysched> royschedList = new ArrayList<>();
        royschedList.add(testRoysched);
        
        when(royschedRepository.findByTitle_TitleId("TC1025")).thenReturn(royschedList);
        
        List<Roysched> result = royschedService.getRoyschedByTitle("TC1025");
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(royschedRepository, times(1)).findByTitle_TitleId("TC1025");
    }

    @Test
    public void testUpdateRoysched() {
        Roysched updatedRoysched = new Roysched();
        updatedRoysched.setRoyschedId(1);
        updatedRoysched.setLorange(1);
        updatedRoysched.setHirange(10000);
        updatedRoysched.setRoyalty(12);

        when(royschedRepository.findById(1)).thenReturn(java.util.Optional.of(testRoysched));
        when(royschedRepository.save(any(Roysched.class))).thenReturn(updatedRoysched);
        
        Roysched result = royschedService.updateRoysched(1, updatedRoysched);
        
        assertNotNull(result);
        verify(royschedRepository, times(1)).findById(1);
        verify(royschedRepository, times(1)).save(any(Roysched.class));
    }
}
