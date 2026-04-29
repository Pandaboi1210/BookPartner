package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.SalesRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.SalesResponseDTO;
import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.Store;
import com.sprint.BookPartnerApplication.repository.SalesRepository;
import com.sprint.BookPartnerApplication.repository.StoreRepository;
import com.sprint.BookPartnerApplication.repository.TitleRepository;
import com.sprint.BookPartnerApplication.servicesImpl.SalesServiceImpl;

import org.junit.jupiter.api.*;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SalesServiceTest {

    @Mock private SalesRepository salesRepository;
    @Mock private StoreRepository storeRepository;
    @Mock private TitleRepository titleRepository;

    @InjectMocks private SalesServiceImpl salesService;

    private SalesRequestDTO dto;
    private Sales sales;
    private Store store;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dto = new SalesRequestDTO();
        dto.setStorId("7066");
        dto.setOrdNum("QA123");
        dto.setTitleId("T1");
        dto.setOrdDate(LocalDateTime.now());
        dto.setQty((short) 10);
        dto.setPayterms("Net 30");

        sales = new Sales();
        sales.setStorId("7066");
        sales.setOrdNum("QA123");
        sales.setTitleId("T1");
        sales.setOrdDate(LocalDateTime.now());
        sales.setQty((short) 10);
        sales.setPayterms("Net 30");

        store = new Store();
        store.setStorId("7066");
    }

    // Test successful sale creation
    @Test
    void testCreateSale_Success() {
        when(storeRepository.findById("7066")).thenReturn(Optional.of(store));
        when(titleRepository.existsById("T1")).thenReturn(true);
        when(salesRepository.save(any())).thenReturn(sales);

        SalesResponseDTO result = salesService.createSale(dto);

        assertNotNull(result);
        verify(salesRepository).save(any());
    }

    // Test create sale when store is not found
    @Test
    void testCreateSale_StoreNotFound() {
        when(storeRepository.findById("7066")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> salesService.createSale(dto));
    }

    // Test create sale when title is not found
    @Test
    void testCreateSale_TitleNotFound() {
        when(storeRepository.findById("7066")).thenReturn(Optional.of(store));
        when(titleRepository.existsById("T1")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> salesService.createSale(dto));
    }

    // Test getting all sales when data exists
    @Test
    void testGetAllSales_WithData() {
        when(salesRepository.findAll()).thenReturn(List.of(sales));

        assertEquals(1, salesService.getAllSales().size());
    }

    // Test getting all sales when no data exists
    @Test
    void testGetAllSales_Empty_ThrowsException() {
        when(salesRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> salesService.getAllSales());
    }

    // Test getting sales by store when data exists
    @Test
    void testGetSalesByStore_WithData() {
        when(storeRepository.findById("7066")).thenReturn(Optional.of(store));
        when(salesRepository.findByStorId("7066")).thenReturn(List.of(sales));

        assertEquals(1, salesService.getSalesByStore("7066").size());
    }

    // Test getting sales by store when no sales exist
    @Test
    void testGetSalesByStore_NoSales_ThrowsException() {
        when(storeRepository.findById("7066")).thenReturn(Optional.of(store));
        when(salesRepository.findByStorId("7066")).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class,
                () -> salesService.getSalesByStore("7066"));
    }

    // Test getting sales when store is not found
    @Test
    void testGetSalesByStore_StoreNotFound() {
        when(storeRepository.findById("9999")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> salesService.getSalesByStore("9999"));
    }

    // Test getting sales by title
    @Test
    void testGetSalesByTitle() {
        when(salesRepository.findByTitleId("T1")).thenReturn(List.of(sales));

        assertEquals(1, salesService.getSalesByTitle("T1").size());
    }

    // Test getting sales by date range
    @Test
    void testGetSalesByDateRange() {
        LocalDateTime from = LocalDateTime.now().minusDays(5);
        LocalDateTime to = LocalDateTime.now();

        when(salesRepository.findByOrdDateBetween(from, to)).thenReturn(List.of(sales));

        assertEquals(1, salesService.getSalesByDateRange(from, to).size());
    }
}