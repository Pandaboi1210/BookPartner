package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.TitleAuthorRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.TitleAuthorResponseDTO;
import com.sprint.BookPartnerApplication.entity.Authors;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.entity.TitleAuthor;
import com.sprint.BookPartnerApplication.entity.TitleAuthorId;
import com.sprint.BookPartnerApplication.exception.DuplicateResourceException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
import com.sprint.BookPartnerApplication.repository.TitleAuthorRepository;
import com.sprint.BookPartnerApplication.servicesImpl.TitleAuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TitleAuthorServiceTest {

    @Mock
    private TitleAuthorRepository titleAuthorRepository;

    @InjectMocks
    private TitleAuthorServiceImpl titleAuthorService;

    private TitleAuthorRequestDTO testRequestDTO;
    private TitleAuthor testTitleAuthor;
    private TitleAuthor hydratedTitleAuthor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // 1. Setup Request DTO
        testRequestDTO = new TitleAuthorRequestDTO();
        testRequestDTO.setAuId("001-01-0001");
        testRequestDTO.setTitleId("TC1025");
        testRequestDTO.setAuOrd((byte) 1);
        testRequestDTO.setRoyaltyper(100);
        
        // 2. Setup standard Entity (Before Hydration - no names)
        testTitleAuthor = new TitleAuthor();
        testTitleAuthor.setAuId("001-01-0001");
        testTitleAuthor.setTitleId("TC1025");
        testTitleAuthor.setAuOrd((byte) 1);
        testTitleAuthor.setRoyaltyper(100);

        // 3. Setup "Hydrated" Entity (Contains the joined Author and Title names)
        Authors mockAuthor = new Authors();
        mockAuthor.setAuFname("Abraham");
        mockAuthor.setAuLname("Bennet");

        Title mockTitle = new Title();
        mockTitle.setTitle("The Busy Executive's Database Guide");

        hydratedTitleAuthor = new TitleAuthor();
        hydratedTitleAuthor.setAuId("001-01-0001");
        hydratedTitleAuthor.setTitleId("TC1025");
        hydratedTitleAuthor.setAuOrd((byte) 1);
        hydratedTitleAuthor.setRoyaltyper(100);
        hydratedTitleAuthor.setAuthor(mockAuthor); // Attaching Author
        hydratedTitleAuthor.setTitle(mockTitle);   // Attaching Title
    }

    // ==========================================
    // POSITIVE SCENARIOS & EDGE CASES (5 Tests)
    // ==========================================

    // TC 1: Standard successful creation
    @Test
    public void testCreateTitleAuthor_Success() {
        when(titleAuthorRepository.existsById(any(TitleAuthorId.class))).thenReturn(false);
        when(titleAuthorRepository.save(any(TitleAuthor.class))).thenReturn(testTitleAuthor);
        when(titleAuthorRepository.findById(any(TitleAuthorId.class))).thenReturn(Optional.of(testTitleAuthor));
        
        TitleAuthorResponseDTO result = titleAuthorService.createTitleAuthor(testRequestDTO);
        
        assertNotNull(result);
        assertEquals("001-01-0001", result.getAuId());
        assertEquals("TC1025", result.getTitleId());
        verify(titleAuthorRepository, times(1)).save(any(TitleAuthor.class));
    }

    // TC 2: Verify the "Hydration" logic correctly maps the Author and Book names to the JSON
    @Test
    public void testCreateTitleAuthor_HydrationSuccess() {
        when(titleAuthorRepository.existsById(any(TitleAuthorId.class))).thenReturn(false);
        when(titleAuthorRepository.save(any(TitleAuthor.class))).thenReturn(testTitleAuthor);
        
        // Return the fully hydrated entity containing the names
        when(titleAuthorRepository.findById(any(TitleAuthorId.class))).thenReturn(Optional.of(hydratedTitleAuthor));
        
        TitleAuthorResponseDTO result = titleAuthorService.createTitleAuthor(testRequestDTO);
        
        assertNotNull(result);
        // Assert the names were successfully flattened!
        assertEquals("Abraham Bennet", result.getAuthorFullName());
        assertEquals("The Busy Executive's Database Guide", result.getBookTitle());
    }

    // TC 3: Verify fallback behavior if Hydration fails (database doesn't return the full objects)
    @Test
    public void testCreateTitleAuthor_FallbackIfHydrationFails() {
        when(titleAuthorRepository.existsById(any(TitleAuthorId.class))).thenReturn(false);
        when(titleAuthorRepository.save(any(TitleAuthor.class))).thenReturn(testTitleAuthor);
        
        // Return an empty optional (hydration failed)
        when(titleAuthorRepository.findById(any(TitleAuthorId.class))).thenReturn(Optional.empty());
        
        TitleAuthorResponseDTO result = titleAuthorService.createTitleAuthor(testRequestDTO);
        
        assertNotNull(result);
        assertEquals("001-01-0001", result.getAuId());
        // Names should safely be null without crashing the application
        assertNull(result.getAuthorFullName());
    }

    // TC 4: Standard successful deletion
    @Test
    public void testDeleteByAuthorAndTitle_Success() {
        when(titleAuthorRepository.existsById(any(TitleAuthorId.class))).thenReturn(true);
        doNothing().when(titleAuthorRepository).deleteById(any(TitleAuthorId.class));
        
        titleAuthorService.deleteByAuthorAndTitle("001-01-0001", "TC1025");
        
        verify(titleAuthorRepository, times(1)).existsById(any(TitleAuthorId.class));
        verify(titleAuthorRepository, times(1)).deleteById(any(TitleAuthorId.class));
    }

    // ==========================================
    // NEGATIVE SCENARIOS (4 Tests)
    // ==========================================

    // TC 5: Attempting to map an author/title combo that already exists
    @Test
    public void testCreateTitleAuthor_DuplicateThrowsException() {
        // Tell the mock database that this mapping already exists
        when(titleAuthorRepository.existsById(any(TitleAuthorId.class))).thenReturn(true);
        
        assertThrows(DuplicateResourceException.class, () -> {
            titleAuthorService.createTitleAuthor(testRequestDTO);
        });
    }

    // TC 6: Proving the database save method is BLOCKED on duplicates
    @Test
    public void testCreateTitleAuthor_NeverSavedOnDuplicate() {
        when(titleAuthorRepository.existsById(any(TitleAuthorId.class))).thenReturn(true);
        
        try {
            titleAuthorService.createTitleAuthor(testRequestDTO);
        } catch (DuplicateResourceException e) {
            // Ignored for this test, we just want to verify the save behavior
        }
        
        // The most important line: PROVE save() was blocked!
        verify(titleAuthorRepository, never()).save(any(TitleAuthor.class));
    }

    // TC 7: Attempting to delete a mapping that doesn't exist
    @Test
    public void testDeleteByAuthorAndTitle_NotFoundThrowsException() {
        // Tell the mock database this mapping isn't there
        when(titleAuthorRepository.existsById(any(TitleAuthorId.class))).thenReturn(false);
        
        assertThrows(ResourceNotFoundException.class, () -> {
            titleAuthorService.deleteByAuthorAndTitle("INVALID", "INVALID");
        });
    }

    // TC 8: Proving the database delete method is BLOCKED if mapping is missing
    @Test
    public void testDeleteByAuthorAndTitle_NeverDeletedOnNotFound() {
        when(titleAuthorRepository.existsById(any(TitleAuthorId.class))).thenReturn(false);
        
        try {
            titleAuthorService.deleteByAuthorAndTitle("INVALID", "INVALID");
        } catch (ResourceNotFoundException e) {
            // Ignored for this test
        }
        
        // PROVE deleteById() was blocked to prevent a database crash!
        verify(titleAuthorRepository, never()).deleteById(any(TitleAuthorId.class));
    }
}