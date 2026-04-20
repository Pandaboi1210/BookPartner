package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.AuthorsRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.AuthorsResponseDTO;
import com.sprint.BookPartnerApplication.entity.Authors;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.repository.AuthorsRepository;
import com.sprint.BookPartnerApplication.servicesImpl.AuthorsServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorsServiceTest {

    @Mock
    private AuthorsRepository authorsRepository;

    @InjectMocks
    private AuthorsServiceImpl authorsService;

    private AuthorsRequestDTO testAuthorRequestDTO;
    private Authors testAuthor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

      
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
        verify(authorsRepository, times(1)).save(any(Authors.class));
    }

    @Test
    public void testCreateAuthor_NullInput() {
        assertThrows(Exception.class, () -> {
            authorsService.createAuthor(null);
        });
    }

    @Test
    public void testGetAuthorById() {
        when(authorsRepository.findById("001-01-0001"))
                .thenReturn(Optional.of(testAuthor));

        AuthorsResponseDTO result = authorsService.getAuthorById("001-01-0001");

        assertNotNull(result);
        assertEquals("001-01-0001", result.getAuId());
        verify(authorsRepository, times(1)).findById("001-01-0001");
    }


    @Test
    public void testGetAuthorById_NotFound() {
        when(authorsRepository.findById("999"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            authorsService.getAuthorById("999");
        });
    }

 
    @Test
    public void testGetAllAuthors() {
        List<Authors> list = new ArrayList<>();
        list.add(testAuthor);

        when(authorsRepository.findAll()).thenReturn(list);

        List<AuthorsResponseDTO> result = authorsService.getAllAuthors();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

 
    @Test
    public void testUpdateAuthor() {
        when(authorsRepository.findById("001-01-0001"))
                .thenReturn(Optional.of(testAuthor));
        when(authorsRepository.save(any(Authors.class))).thenReturn(testAuthor);

        AuthorsResponseDTO result =
                authorsService.updateAuthor("001-01-0001", testAuthorRequestDTO);

        assertNotNull(result);
        verify(authorsRepository, times(1)).save(any(Authors.class));
    }

 
    @Test
    public void testUpdateAuthor_NullInput() {
        when(authorsRepository.findById("001-01-0001"))
                .thenReturn(Optional.of(testAuthor));

        assertThrows(Exception.class, () -> {
            authorsService.updateAuthor("001-01-0001", null);
        });
    }


    @Test
    public void testDeleteAuthor() {
        when(authorsRepository.findById("001-01-0001"))
                .thenReturn(Optional.of(testAuthor));
        when(authorsRepository.getTitlesByAuthorId("001-01-0001"))
                .thenReturn(new ArrayList<>());

        doNothing().when(authorsRepository).delete(testAuthor);

        authorsService.deleteAuthor("001-01-0001");

        verify(authorsRepository, times(1)).delete(testAuthor);
    }

 
    @Test
    public void testDeleteAuthor_WithTitles() {
        List<Title> titles = new ArrayList<>();
        titles.add(new Title());

        when(authorsRepository.findById("001-01-0001"))
                .thenReturn(Optional.of(testAuthor));
        when(authorsRepository.getTitlesByAuthorId("001-01-0001"))
                .thenReturn(titles);

        assertThrows(RuntimeException.class, () -> {
            authorsService.deleteAuthor("001-01-0001");
        });
    }
}