package com.sprint.BookPartnerApplication.services;

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

    private Discounts testDiscount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Test data - CREATE
        testDiscount = new Discounts();
        testDiscount.setDiscountId(1);
        testDiscount.setDiscounttype("BULK");
        testDiscount.setLowqty(10);
        testDiscount.setHighqty(50);
        testDiscount.setDiscount(new BigDecimal("15.00"));
    }

    @Test
    public void testCreateDiscount() {
        when(discountRepository.save(testDiscount)).thenReturn(testDiscount);
        
        Discounts result = discountService.createDiscount(testDiscount);
        
        assertNotNull(result);
        assertEquals("BULK", result.getDiscounttype());
        assertEquals(new BigDecimal("15.00"), result.getDiscount());
        verify(discountRepository, times(1)).save(testDiscount);
    }

    @Test
    public void testGetAllDiscounts() {
        List<Discounts> discountsList = new ArrayList<>();
        discountsList.add(testDiscount);
        
        when(discountRepository.findAll()).thenReturn(discountsList);
        
        List<Discounts> result = discountService.getAllDiscounts();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(discountRepository, times(1)).findAll();
    }


    @Test
    public void testUpdateDiscountByType() {
        java.util.List<Discounts> discountsList = new ArrayList<>();
        Discounts updatedDiscount = new Discounts();
        updatedDiscount.setDiscounttype("BULK");
        updatedDiscount.setLowqty(20);
        updatedDiscount.setHighqty(100);
        updatedDiscount.setDiscount(new BigDecimal("20.00"));
        discountsList.add(testDiscount);

        when(discountRepository.findDiscountsByType("BULK")).thenReturn(discountsList);
        when(discountRepository.save(any(Discounts.class))).thenReturn(updatedDiscount);
        
        Discounts result = discountService.updateDiscountByType("BULK", updatedDiscount);
        
        assertNotNull(result);
        verify(discountRepository, times(1)).findDiscountsByType("BULK");
        verify(discountRepository, times(1)).save(any(Discounts.class));
    }
}
