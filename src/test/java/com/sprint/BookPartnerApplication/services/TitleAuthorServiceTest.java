package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.TitleAuthorRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.TitleAuthorResponseDTO;
import com.sprint.BookPartnerApplication.entity.TitleAuthor;
import com.sprint.BookPartnerApplication.entity.TitleAuthorId;
import com.sprint.BookPartnerApplication.repository.TitleAuthorRepository;
import com.sprint.BookPartnerApplication.servicesImpl.TitleAuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TitleAuthorServiceTest {

    @Mock
    private TitleAuthorRepository titleAuthorRepository;

    @InjectMocks
    private TitleAuthorServiceImpl titleAuthorService;

    private TitleAuthorRequestDTO testTitleAuthorRequestDTO;
    private TitleAuthor testTitleAuthor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Test data - CREATE (RequestDTO)
        testTitleAuthorRequestDTO = new TitleAuthorRequestDTO();
        testTitleAuthorRequestDTO.setAuId("001-01-0001");
        testTitleAuthorRequestDTO.setTitleId("TC1025");
        testTitleAuthorRequestDTO.setAuOrd((byte) 1);
        testTitleAuthorRequestDTO.setRoyaltyper(100);
        
        // Test data - Entity (for mocking repository)
        testTitleAuthor = new TitleAuthor();
        testTitleAuthor.setAuId("001-01-0001");
        testTitleAuthor.setTitleId("TC1025");
        testTitleAuthor.setAuOrd((byte) 1);
        testTitleAuthor.setRoyaltyper(100);
    }

    @Test
    public void testCreateTitleAuthor() {
        when(titleAuthorRepository.save(any(TitleAuthor.class))).thenReturn(testTitleAuthor);
        
        TitleAuthorResponseDTO result = titleAuthorService.createTitleAuthor(testTitleAuthorRequestDTO);
        
        assertNotNull(result);
        assertEquals("001-01-0001", result.getAuId());
        assertEquals("TC1025", result.getTitleId());
        assertEquals(100, result.getRoyaltyper());
        verify(titleAuthorRepository, times(1)).save(any(TitleAuthor.class));
    }

    @Test
    public void testDeleteByAuthorAndTitle() {
        TitleAuthorId id = new TitleAuthorId();
        id.setAuId("001-01-0001");
        id.setTitleId("TC1025");
        
        when(titleAuthorRepository.existsById(id)).thenReturn(true);
        doNothing().when(titleAuthorRepository).deleteById(id);
        
        titleAuthorService.deleteByAuthorAndTitle("001-01-0001", "TC1025");
        
        verify(titleAuthorRepository, times(1)).existsById(id);
        verify(titleAuthorRepository, times(1)).deleteById(id);
    }
}
