package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.SalesRequestDTO;
import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.Store;
import com.sprint.BookPartnerApplication.repository.SalesRepository;
import com.sprint.BookPartnerApplication.repository.StoreRepository;
import com.sprint.BookPartnerApplication.repository.TitleRepository;
import com.sprint.BookPartnerApplication.servicesImpl.SalesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SalesServiceTest {

    @Mock
    private SalesRepository salesRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private TitleRepository titleRepository;

    @InjectMocks
    private SalesServiceImpl salesService;

    private SalesRequestDTO testSalesRequestDTO;
    private Sales testSales;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Test data - CREATE (RequestDTO)
        testSalesRequestDTO = new SalesRequestDTO();
        testSalesRequestDTO.setStorId("7066");
        testSalesRequestDTO.setOrdNum("QA879.1");
        testSalesRequestDTO.setTitleId("TC1025");
        testSalesRequestDTO.setOrdDate(LocalDateTime.now());
        testSalesRequestDTO.setQty((short) 75);
        testSalesRequestDTO.setPayterms("Net 30");
        
        // Test data - Entity (for mocking repository)
        testSales = new Sales();
        testSales.setStorId("7066");
        testSales.setOrdNum("QA879.1");
        testSales.setTitleId("TC1025");
        testSales.setOrdDate(LocalDateTime.now());
        testSales.setQty((short) 75);
        testSales.setPayterms("Net 30");
    }

    @Test
    public void testCreateSale() {
        Store store = new Store();
        store.setStorId("7066");
        when(storeRepository.findById("7066")).thenReturn(java.util.Optional.of(store));
        when(titleRepository.existsById("TC1025")).thenReturn(true);
        when(salesRepository.save(any(Sales.class))).thenReturn(testSales);
        
        Sales result = salesService.createSale(testSalesRequestDTO);
        
        assertNotNull(result);
        assertEquals("7066", result.getStorId());
        assertEquals("QA879.1", result.getOrdNum());
        verify(salesRepository, times(1)).save(any(Sales.class));
    }

    @Test
    public void testGetAllSales() {
        List<Sales> salesList = new ArrayList<>();
        salesList.add(testSales);
        
        when(salesRepository.findAll()).thenReturn(salesList);
        
        List<Sales> result = salesService.getAllSales();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(salesRepository, times(1)).findAll();
    }

    @Test
    public void testGetSalesByStore() {
        List<Sales> salesList = new ArrayList<>();
        salesList.add(testSales);
        
        Store store = new Store();
        store.setStorId("7066");
        when(storeRepository.findById("7066")).thenReturn(java.util.Optional.of(store));
        when(salesRepository.findByStorId("7066")).thenReturn(salesList);
        
        List<Sales> result = salesService.getSalesByStore("7066");
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(salesRepository, times(1)).findByStorId("7066");
    }

    @Test
    public void testGetSalesByTitle() {
        List<Sales> salesList = new ArrayList<>();
        salesList.add(testSales);
        
        when(salesRepository.findByTitleId("TC1025")).thenReturn(salesList);
        
        List<Sales> result = salesService.getSalesByTitle("TC1025");
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(salesRepository, times(1)).findByTitleId("TC1025");
    }

    @Test
    public void testGetSalesByDateRange() {
        LocalDateTime from = LocalDateTime.now().minusDays(7);
        LocalDateTime to = LocalDateTime.now();
        List<Sales> salesList = new ArrayList<>();
        salesList.add(testSales);
        
        when(salesRepository.findByOrdDateBetween(from, to)).thenReturn(salesList);
        
        List<Sales> result = salesService.getSalesByDateRange(from, to);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(salesRepository, times(1)).findByOrdDateBetween(from, to);
    }
}
