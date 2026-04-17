package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.StoreRequestDTO;
import com.sprint.BookPartnerApplication.entity.Store;
import com.sprint.BookPartnerApplication.repository.StoreRepository;
import com.sprint.BookPartnerApplication.servicesImpl.StoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreServiceImpl storeService;

    private StoreRequestDTO testStoreRequestDTO;
    private Store testStore;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Test data - CREATE (RequestDTO)
        testStoreRequestDTO = new StoreRequestDTO();
        testStoreRequestDTO.setStorId("7066");
        testStoreRequestDTO.setStorName("Bookbeat");
        testStoreRequestDTO.setStorAddress("679 E. Hastings");
        testStoreRequestDTO.setCity("Los Angeles");
        testStoreRequestDTO.setState("CA");
        testStoreRequestDTO.setZip("90001");
        
        // Test data - Entity (for mocking repository)
        testStore = new Store();
        testStore.setStorId("7066");
        testStore.setStorName("Bookbeat");
        testStore.setStorAddress("679 E. Hastings");
        testStore.setCity("Los Angeles");
        testStore.setState("CA");
        testStore.setZip("90001");
    }

    @Test
    public void testCreateStore() {
        when(storeRepository.save(any(Store.class))).thenReturn(testStore);
        
        Store result = storeService.createStore(testStoreRequestDTO);
        
        assertNotNull(result);
        assertEquals("7066", result.getStorId());
        assertEquals("Bookbeat", result.getStorName());
        verify(storeRepository, times(1)).save(any(Store.class));
    }

    @Test
    public void testGetStoreById() {
        when(storeRepository.findById("7066")).thenReturn(java.util.Optional.of(testStore));
        
        Store result = storeService.getStoreById("7066");
        
        assertNotNull(result);
        assertEquals("7066", result.getStorId());
        verify(storeRepository, times(1)).findById("7066");
    }

    @Test
    public void testGetAllStores() {
        List<Store> storeList = new ArrayList<>();
        storeList.add(testStore);
        
        when(storeRepository.findAll()).thenReturn(storeList);
        
        List<Store> result = storeService.getAllStores();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(storeRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateStore() {
        StoreRequestDTO updatedStoreDTO = new StoreRequestDTO();
        updatedStoreDTO.setStorId("7066");
        updatedStoreDTO.setStorName("Bookbeat Central");
        updatedStoreDTO.setStorAddress("456 Central Ave");
        updatedStoreDTO.setCity("Chicago");
        updatedStoreDTO.setState("IL");
        updatedStoreDTO.setZip("60601");
        
        Store updatedStore = new Store();
        updatedStore.setStorId("7066");
        updatedStore.setStorName("Bookbeat Central");
        updatedStore.setStorAddress("456 Central Ave");
        updatedStore.setCity("Chicago");
        updatedStore.setState("IL");
        updatedStore.setZip("60601");

        when(storeRepository.findById("7066")).thenReturn(java.util.Optional.of(testStore));
        when(storeRepository.save(any(Store.class))).thenReturn(updatedStore);
        
        Store result = storeService.updateStore("7066", updatedStoreDTO);
        
        assertNotNull(result);
        verify(storeRepository, times(1)).findById("7066");
        verify(storeRepository, times(1)).save(any(Store.class));
    }
}
