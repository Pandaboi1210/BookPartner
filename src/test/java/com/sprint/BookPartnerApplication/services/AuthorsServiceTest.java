package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.AuthorsRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.AuthorsResponseDTO;
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

    private AuthorsRequestDTO testAuthorRequestDTO;
    private AuthorsResponseDTO testAuthorResponseDTO;
    private Authors testAuthor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Test data - CREATE (RequestDTO)
        testAuthorRequestDTO = new AuthorsRequestDTO();
        testAuthorRequestDTO.setAuId("001-01-0001");
        testAuthorRequestDTO.setAuFname("John");
        testAuthorRequestDTO.setAuLname("Doe");
        testAuthorRequestDTO.setPhone("5551234567");
        testAuthorRequestDTO.setAddress("123 Main St");
        testAuthorRequestDTO.setCity("New York");
        testAuthorRequestDTO.setState("NY");
        testAuthorRequestDTO.setZip("10001");
        testAuthorRequestDTO.setContract(1);
        
        // Test data - Response DTO
        testAuthorResponseDTO = new AuthorsResponseDTO();
        testAuthorResponseDTO.setAuId("001-01-0001");
        testAuthorResponseDTO.setAuFname("John");
        testAuthorResponseDTO.setAuLname("Doe");
        testAuthorResponseDTO.setPhone("5551234567");
        testAuthorResponseDTO.setCity("New York");
        testAuthorResponseDTO.setState("NY");
        
        // Test data - Entity (for mocking repository)
        testAuthor = new Authors();
        testAuthor.setAuId("001-01-0001");
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
        when(authorsRepository.save(any(Authors.class))).thenReturn(testAuthor);
        
        AuthorsResponseDTO result = authorsService.createAuthor(testAuthorRequestDTO);
        
        assertNotNull(result);
        assertEquals("001-01-0001", result.getAuId());
        assertEquals("John", result.getAuFname());
        assertEquals("Doe", result.getAuLname());
        verify(authorsRepository, times(1)).save(any(Authors.class));
    }

    @Test
    public void testGetAuthorById() {
        when(authorsRepository.findById("001-01-0001")).thenReturn(java.util.Optional.of(testAuthor));
        
        AuthorsResponseDTO result = authorsService.getAuthorById("001-01-0001");
        
        assertNotNull(result);
        assertEquals("001-01-0001", result.getAuId());
        verify(authorsRepository, times(1)).findById("001-01-0001");
    }

    @Test
    public void testGetAllAuthors() {
        java.util.List<Authors> authorsList = new java.util.ArrayList<>();
        authorsList.add(testAuthor);
        
        when(authorsRepository.findAll()).thenReturn(authorsList);
        
        java.util.List<AuthorsResponseDTO> result = authorsService.getAllAuthors();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(authorsRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateAuthor() {
        AuthorsRequestDTO updatedAuthorDTO = new AuthorsRequestDTO();
        updatedAuthorDTO.setAuId("001-01-0001");
        updatedAuthorDTO.setAuFname("Jane");
        updatedAuthorDTO.setAuLname("Smith");
        updatedAuthorDTO.setPhone("5559876543");
        updatedAuthorDTO.setAddress("123 Main St");
        updatedAuthorDTO.setCity("New York");
        updatedAuthorDTO.setState("NY");
        updatedAuthorDTO.setZip("10001");
        updatedAuthorDTO.setContract(1);
        
        Authors updatedAuthor = new Authors();
        updatedAuthor.setAuId("001-01-0001");
        updatedAuthor.setAuFname("Jane");
        updatedAuthor.setAuLname("Smith");
        updatedAuthor.setPhone("5559876543");

        when(authorsRepository.findById("001-01-0001")).thenReturn(java.util.Optional.of(testAuthor));
        when(authorsRepository.save(any(Authors.class))).thenReturn(updatedAuthor);
        
        AuthorsResponseDTO result = authorsService.updateAuthor("001-01-0001", updatedAuthorDTO);
        
        assertNotNull(result);
        verify(authorsRepository, times(1)).findById("001-01-0001");
        verify(authorsRepository, times(1)).save(any(Authors.class));
    }

    @Test
    public void testDeleteAuthor() {
        when(authorsRepository.findById("001-01-0001")).thenReturn(java.util.Optional.of(testAuthor));
        when(authorsRepository.getTitlesByAuthorId("001-01-0001")).thenReturn(new java.util.ArrayList<>());
        doNothing().when(authorsRepository).delete(testAuthor);
        
        authorsService.deleteAuthor("001-01-0001");
        
        verify(authorsRepository, times(1)).findById("001-01-0001");
        verify(authorsRepository, times(1)).getTitlesByAuthorId("001-01-0001");
        verify(authorsRepository, times(1)).delete(testAuthor);
    }
}
