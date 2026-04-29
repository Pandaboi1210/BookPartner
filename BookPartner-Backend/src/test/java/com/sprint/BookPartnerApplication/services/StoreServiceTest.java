package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.StoreRequestDTO;
import com.sprint.BookPartnerApplication.entity.*;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
import com.sprint.BookPartnerApplication.repository.*;
import com.sprint.BookPartnerApplication.servicesImpl.StoreServiceImpl;

import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StoreServiceTest {

    @Mock private StoreRepository storeRepository;
    @Mock private SalesRepository salesRepository;
    @Mock private DiscountRepository discountsRepository;

    @InjectMocks private StoreServiceImpl storeService;

    private Store store;
    private StoreRequestDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dto = new StoreRequestDTO();
        dto.setStorId("7066");
        dto.setStorName("Bookbeat");

        store = new Store();
        store.setStorId("7066");
        store.setStorName("Bookbeat");
    }

    // Test create store
    @Test
    void testCreateStore() {
        when(storeRepository.existsById("7066")).thenReturn(false);
        when(storeRepository.save(any())).thenReturn(store);

        assertNotNull(storeService.createStore(dto));
    }

    // Test get store by id
    @Test
    void testGetStoreById() {
        when(storeRepository.findById("7066")).thenReturn(Optional.of(store));

        assertNotNull(storeService.getStoreById("7066"));
    }

    // Test get store by id when not found
    @Test
    void testGetStoreById_NotFound() {
        when(storeRepository.findById("7066")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> storeService.getStoreById("7066"));
    }

    // Test get all stores with data
    @Test
    void testGetAllStores_WithData() {
        when(storeRepository.findAll()).thenReturn(List.of(store));

        assertEquals(1, storeService.getAllStores().size());
    }

    // Test get all stores when empty
    @Test
    void testGetAllStores_Empty_ThrowsException() {
        when(storeRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class,
                () -> storeService.getAllStores());
    }

    // Test update store
    @Test
    void testUpdateStore() {
        when(storeRepository.findById("7066")).thenReturn(Optional.of(store));
        when(storeRepository.save(any())).thenReturn(store);

        assertNotNull(storeService.updateStore("7066", dto));
    }

    // Test update store when not found
    @Test
    void testUpdateStore_NotFound() {
        when(storeRepository.findById("7066")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> storeService.updateStore("7066", dto));
    }

    // Test get sales by store
    @Test
    void testGetSalesByStore() {
        Sales sale = new Sales();
        sale.setStorId("7066");

        when(storeRepository.findById("7066")).thenReturn(Optional.of(store));
        when(salesRepository.findByStorId("7066")).thenReturn(List.of(sale));

        assertEquals(1, storeService.getSalesByStore("7066").size());
    }

    // Test get sales by store when no sales exist
    @Test
    void testGetSalesByStore_NoSales() {
        when(storeRepository.findById("7066")).thenReturn(Optional.of(store));
        when(salesRepository.findByStorId("7066")).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class,
                () -> storeService.getSalesByStore("7066"));
    }

    // Test get discounts by store
    @Test
    void testGetDiscountsByStore() {
        Discounts d = new Discounts();
        d.setDiscounttype("BULK");

        when(storeRepository.findById("7066")).thenReturn(Optional.of(store));
        when(discountsRepository.findDiscountsByStoreId("7066")).thenReturn(List.of(d));

        assertEquals(1, storeService.getDiscountsByStore("7066").size());
    }

    // Test get discounts by store when none exist
    @Test
    void testGetDiscountsByStore_NoDiscounts() {
        when(storeRepository.findById("7066")).thenReturn(Optional.of(store));
        when(discountsRepository.findDiscountsByStoreId("7066"))
                .thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class,
                () -> storeService.getDiscountsByStore("7066"));
    }
}