package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.DiscountRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.DiscountResponseDTO;
import com.sprint.BookPartnerApplication.entity.Discounts;
import com.sprint.BookPartnerApplication.entity.Store;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
import com.sprint.BookPartnerApplication.repository.DiscountRepository;
import com.sprint.BookPartnerApplication.repository.StoreRepository;
import com.sprint.BookPartnerApplication.servicesImpl.DiscountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DiscountServiceTest {

    @Mock
    private DiscountRepository discountRepository;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private DiscountServiceImpl discountService;
    private DiscountRequestDTO testDiscountRequestDTO;
    private Discounts testDiscount;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testDiscountRequestDTO = new DiscountRequestDTO();
        testDiscountRequestDTO.setDiscounttype("BULK");
        testDiscountRequestDTO.setLowqty(10);
        testDiscountRequestDTO.setHighqty(50);
        testDiscountRequestDTO.setDiscount(new BigDecimal("15.00"));

        testDiscount = new Discounts();
        testDiscount.setDiscountId(1);
        testDiscount.setDiscounttype("BULK");
        testDiscount.setLowqty(10);
        testDiscount.setHighqty(50);
        testDiscount.setDiscount(new BigDecimal("15.00"));
    }

    // ── Test 1 (existing) ──────────────────────────────────────────────────────
    @Test
    public void testCreateDiscount() {
        when(discountRepository.save(any(Discounts.class))).thenReturn(testDiscount);

        DiscountResponseDTO result = discountService.createDiscount(testDiscountRequestDTO);

        assertNotNull(result);
        assertEquals("BULK", result.getDiscounttype());
        assertEquals(new BigDecimal("15.00"), result.getDiscount());
        verify(discountRepository, times(1)).save(any(Discounts.class));
    }

    // ── Test 2 (existing) ──────────────────────────────────────────────────────
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

    // ── Test 3 (existing) ──────────────────────────────────────────────────────
    @Test
    public void testUpdateDiscountByType() {
        List<Discounts> discountsList = new ArrayList<>();
        discountsList.add(testDiscount);

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

        when(discountRepository.findDiscountsByType("BULK")).thenReturn(discountsList);
        when(discountRepository.save(any(Discounts.class))).thenReturn(updatedDiscount);

        DiscountResponseDTO result = discountService.updateDiscountByType("BULK", updatedDiscountDTO);

        assertNotNull(result);
        verify(discountRepository, times(1)).findDiscountsByType("BULK");
        verify(discountRepository, times(1)).save(any(Discounts.class));
    }

    // ── Test 4 (new) — getAllDiscounts returns empty when no discounts exist ───
    @Test
    public void testGetAllDiscounts_EmptyList() {
        when(discountRepository.findAll()).thenReturn(Collections.emptyList());

        List<DiscountResponseDTO> result = discountService.getAllDiscounts();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(discountRepository, times(1)).findAll();
    }

    // ── Test 5 (new) — updateDiscountByType throws when type not found ─────────
    @Test
    public void testUpdateDiscountByType_NotFound_ThrowsException() {
        when(discountRepository.findDiscountsByType("SEASONAL")).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class,
                () -> discountService.updateDiscountByType("SEASONAL", testDiscountRequestDTO));
        verify(discountRepository, never()).save(any(Discounts.class));
    }

    // ── Test 6 (new) — createDiscount maps all fields correctly ───────────────
    @Test
    public void testCreateDiscount_MapsFieldsCorrectly() {
        when(discountRepository.save(any(Discounts.class))).thenReturn(testDiscount);

        DiscountResponseDTO result = discountService.createDiscount(testDiscountRequestDTO);

        assertNotNull(result);
        assertEquals(10, result.getLowqty());
        assertEquals(50, result.getHighqty());
        assertEquals(new BigDecimal("15.00"), result.getDiscount());
    }

    // ── Test 7 (new) — getDiscountsByStore returns discounts for a store ───────
    @Test
    public void testGetDiscountsByStore_ReturnsDiscounts() {
        Store mockStore = new Store();
        mockStore.setStorId("7066");
        when(storeRepository.findById("7066")).thenReturn(Optional.of(mockStore));
        when(discountRepository.findDiscountsByStoreId("7066")).thenReturn(List.of(testDiscount));

        List<DiscountResponseDTO> result = discountService.getDiscountsByStore("7066");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(storeRepository, times(1)).findById("7066");
        verify(discountRepository, times(1)).findDiscountsByStoreId("7066");
    }

    // ── Test 8 (new) — getDiscountsByStore throws when no discounts found ──────
    @Test
    public void testGetDiscountsByStore_NoDiscounts_ReturnsEmpty() {
        Store mockStore = new Store();
        mockStore.setStorId("7066");
        when(storeRepository.findById("7066")).thenReturn(Optional.of(mockStore));
        when(discountRepository.findDiscountsByStoreId("7066")).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class,
                () -> discountService.getDiscountsByStore("7066"));
        verify(storeRepository, times(1)).findById("7066");
        verify(discountRepository, times(1)).findDiscountsByStoreId("7066");
    }

    // ── Test 9 (new) — updateDiscountByType updates quantity range correctly ───
    @Test
    public void testUpdateDiscountByType_UpdatesQuantityRange() {
        List<Discounts> discountsList = List.of(testDiscount);

        DiscountRequestDTO updateDTO = new DiscountRequestDTO();
        updateDTO.setDiscounttype("BULK");
        updateDTO.setLowqty(5);
        updateDTO.setHighqty(200);
        updateDTO.setDiscount(new BigDecimal("25.00"));

        Discounts savedDiscount = new Discounts();
        savedDiscount.setDiscountId(1);
        savedDiscount.setDiscounttype("BULK");
        savedDiscount.setLowqty(5);
        savedDiscount.setHighqty(200);
        savedDiscount.setDiscount(new BigDecimal("25.00"));

        when(discountRepository.findDiscountsByType("BULK")).thenReturn(discountsList);
        when(discountRepository.save(any(Discounts.class))).thenReturn(savedDiscount);

        DiscountResponseDTO result = discountService.updateDiscountByType("BULK", updateDTO);

        assertNotNull(result);
        assertEquals(5, result.getLowqty());
        assertEquals(200, result.getHighqty());
        assertEquals(new BigDecimal("25.00"), result.getDiscount());
    }

    // ── Test 10 (new) — getAllDiscounts returns multiple discount types ─────────
    @Test
    public void testGetAllDiscounts_MultipleDiscounts() {
        Discounts seasonal = new Discounts();
        seasonal.setDiscountId(2);
        seasonal.setDiscounttype("SEASONAL");
        seasonal.setLowqty(1);
        seasonal.setHighqty(5);
        seasonal.setDiscount(new BigDecimal("5.00"));

        List<Discounts> discountsList = List.of(testDiscount, seasonal);
        when(discountRepository.findAll()).thenReturn(discountsList);

        List<DiscountResponseDTO> result = discountService.getAllDiscounts();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(discountRepository, times(1)).findAll();
    }
}