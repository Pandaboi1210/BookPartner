package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.Authors;
import com.sprint.BookPartnerApplication.repository.AuthorsRepository;
import com.sprint.BookPartnerApplication.servicesImpl.AuthorsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorsServiceTest {

    @Mock
    private AuthorsRepository authorsRepository;

    @InjectMocks
    private AuthorsServiceImpl authorsService;

    private Authors testAuthor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Test data - CREATE
        testAuthor = new Authors();
        testAuthor.setAuId("A001");
        testAuthor.setAuFname("John");
        testAuthor.setAuLname("Doe");
        testAuthor.setPhone("5551234567");
        testAuthor.setAddress("123 Main St");
        testAuthor.setCity("New York");
        testAuthor.setState("NY");
        testAuthor.setZip("10001");
        testAuthor.setContract(1);
    }

    @Test
    public void testCreateAuthor() {
        when(authorsRepository.save(testAuthor)).thenReturn(testAuthor);
        
        Authors result = authorsService.createAuthor(testAuthor);
        
        assertNotNull(result);
        assertEquals("A001", result.getAuId());
        assertEquals("John", result.getAuFname());
        assertEquals("Doe", result.getAuLname());
        verify(authorsRepository, times(1)).save(testAuthor);
    }

    @Test
    public void testGetAuthorById() {
        when(authorsRepository.findById("A001")).thenReturn(java.util.Optional.of(testAuthor));
        
        Authors result = authorsService.getAuthorById("A001");
        
        assertNotNull(result);
        assertEquals("A001", result.getAuId());
        verify(authorsRepository, times(1)).findById("A001");
    }

    @Test
    public void testGetAllAuthors() {
        java.util.List<Authors> authorsList = new java.util.ArrayList<>();
        authorsList.add(testAuthor);
        
        when(authorsRepository.findAll()).thenReturn(authorsList);
        
        java.util.List<Authors> result = authorsService.getAllAuthors();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(authorsRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateAuthor() {
        Authors updatedAuthor = new Authors();
        updatedAuthor.setAuId("A001");
        updatedAuthor.setAuFname("Jane");
        updatedAuthor.setAuLname("Smith");
        updatedAuthor.setPhone("5559876543");

        when(authorsRepository.findById("A001")).thenReturn(java.util.Optional.of(testAuthor));
        when(authorsRepository.save(any(Authors.class))).thenReturn(updatedAuthor);
        
        Authors result = authorsService.updateAuthor("A001", updatedAuthor);
        
        assertNotNull(result);
        verify(authorsRepository, times(1)).findById("A001");
        verify(authorsRepository, times(1)).save(any(Authors.class));
    }

    @Test
    public void testDeleteAuthor() {
        doNothing().when(authorsRepository).deleteById("A001");
        
        authorsService.deleteAuthor("A001");
        
        verify(authorsRepository, times(1)).deleteById("A001");
    }
}
