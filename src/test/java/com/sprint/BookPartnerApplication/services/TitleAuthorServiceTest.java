package com.sprint.BookPartnerApplication.services;

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

    private TitleAuthor testTitleAuthor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Test data - CREATE
        testTitleAuthor = new TitleAuthor();
        testTitleAuthor.setAuId("A001");
        testTitleAuthor.setTitleId("TC1025");
        testTitleAuthor.setAuOrd((byte) 1);
        testTitleAuthor.setRoyaltyper(100);
    }

    @Test
    public void testCreateTitleAuthor() {
        when(titleAuthorRepository.save(testTitleAuthor)).thenReturn(testTitleAuthor);
        
        TitleAuthor result = titleAuthorService.createTitleAuthor(testTitleAuthor);
        
        assertNotNull(result);
        assertEquals("A001", result.getAuId());
        assertEquals("TC1025", result.getTitleId());
        assertEquals(100, result.getRoyaltyper());
        verify(titleAuthorRepository, times(1)).save(testTitleAuthor);
    }

    @Test
    public void testDeleteByAuthorAndTitle() {
        TitleAuthorId id = new TitleAuthorId();
        id.setAuId("A001");
        id.setTitleId("TC1025");
        
        doNothing().when(titleAuthorRepository).deleteById(id);
        
        titleAuthorService.deleteByAuthorAndTitle("A001", "TC1025");
        
        verify(titleAuthorRepository, times(1)).deleteById(id);
    }
}
