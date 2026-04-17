package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.DiscountRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.DiscountResponseDTO;
import com.sprint.BookPartnerApplication.entity.Discounts;
import com.sprint.BookPartnerApplication.repository.DiscountRepository;
import com.sprint.BookPartnerApplication.servicesImpl.DiscountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DiscountServiceTest {

    @Mock
    private DiscountRepository discountRepository;

    @InjectMocks
    private DiscountServiceImpl discountService;

    private DiscountRequestDTO testDiscountRequestDTO;
    private DiscountResponseDTO testDiscountResponseDTO;
    private Discounts testDiscount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Test data - CREATE (RequestDTO)
        testDiscountRequestDTO = new DiscountRequestDTO();
        testDiscountRequestDTO.setDiscounttype("BULK");
        testDiscountRequestDTO.setLowqty(10);
        testDiscountRequestDTO.setHighqty(50);
        testDiscountRequestDTO.setDiscount(new BigDecimal("15.00"));
        
        // Test data - Response DTO
        testDiscountResponseDTO = new DiscountResponseDTO();
        testDiscountResponseDTO.setDiscounttype("BULK");
        testDiscountResponseDTO.setLowqty(10);
        testDiscountResponseDTO.setHighqty(50);
        testDiscountResponseDTO.setDiscount(new BigDecimal("15.00"));
        
        // Test data - Entity (for mocking repository)
        testDiscount = new Discounts();
        testDiscount.setDiscountId(1);
        testDiscount.setDiscounttype("BULK");
        testDiscount.setLowqty(10);
        testDiscount.setHighqty(50);
        testDiscount.setDiscount(new BigDecimal("15.00"));
    }

    @Test
    public void testCreateDiscount() {
        when(discountRepository.save(any(Discounts.class))).thenReturn(testDiscount);
        
        DiscountResponseDTO result = discountService.createDiscount(testDiscountRequestDTO);
        
        assertNotNull(result);
        assertEquals("BULK", result.getDiscounttype());
        assertEquals(new BigDecimal("15.00"), result.getDiscount());
        verify(discountRepository, times(1)).save(any(Discounts.class));
    }

    @Test
    public void testGetAllDiscounts() {
        List<Discounts> discountsList = new ArrayList<>();
        discountsList.add(testDiscount);
        
        when(discountRepository.findAll()).thenReturn(discountsList);
        
        List<DiscountResponseDTO> result = discountService.getAllDiscounts();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(discountRepository, times(1)).findAll();
    }


    @Test
    public void testUpdateDiscountByType() {
        java.util.List<Discounts> discountsList = new ArrayList<>();
        
        DiscountRequestDTO updatedDiscountDTO = new DiscountRequestDTO();
        updatedDiscountDTO.setDiscounttype("BULK");
        updatedDiscountDTO.setLowqty(20);
        updatedDiscountDTO.setHighqty(100);
        updatedDiscountDTO.setDiscount(new BigDecimal("20.00"));
        
        Discounts updatedDiscount = new Discounts();
        updatedDiscount.setDiscountId(1);
        updatedDiscount.setDiscounttype("BULK");
        updatedDiscount.setLowqty(20);
        updatedDiscount.setHighqty(100);
        updatedDiscount.setDiscount(new BigDecimal("20.00"));
        discountsList.add(testDiscount);

        when(discountRepository.findDiscountsByType("BULK")).thenReturn(discountsList);
        when(discountRepository.save(any(Discounts.class))).thenReturn(updatedDiscount);
        
        DiscountResponseDTO result = discountService.updateDiscountByType("BULK", updatedDiscountDTO);
        
        assertNotNull(result);
        verify(discountRepository, times(1)).findDiscountsByType("BULK");
        verify(discountRepository, times(1)).save(any(Discounts.class));
    }
}
