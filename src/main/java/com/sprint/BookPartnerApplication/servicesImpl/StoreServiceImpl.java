package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.Discounts;
import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.Store;
import com.sprint.BookPartnerApplication.exception.BadRequestException;
import com.sprint.BookPartnerApplication.exception.DuplicateResourceException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
import com.sprint.BookPartnerApplication.repository.DiscountRepository;
import com.sprint.BookPartnerApplication.repository.SalesRepository;
import com.sprint.BookPartnerApplication.repository.StoreRepository;
import com.sprint.BookPartnerApplication.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private DiscountRepository discountsRepository;

    // POST /api/v1/stores
    @Override
    public Store createStore(Store store) {
        if (store.getStorId() == null || store.getStorId().isBlank()) {
            throw new BadRequestException("Store ID must not be blank");
        }
        
        // 🚨 409 CONFLICT: Swapped BadRequestException for DuplicateResourceException
        if (storeRepository.existsById(store.getStorId())) {
            throw new DuplicateResourceException(
                "Store already exists with ID: " + store.getStorId());
        }
        
        return storeRepository.save(store);
    }

    // GET /api/v1/stores
    @Override
    public List<Store> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        if (stores.isEmpty()) {
            throw new ResourceNotFoundException("No stores found");
        }
        return stores;
    }

    // GET /api/v1/stores/{storeId}
    @Override
    public Store getStoreById(String storeId) {
        if (storeId == null || storeId.isBlank()) {
            throw new BadRequestException("Store ID must not be blank");
        }
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Store not found with ID: " + storeId));
    }

    // PUT /api/v1/stores/{storeId}
    @Override
    public Store updateStore(String storeId, Store updatedStore) {
        if (storeId == null || storeId.isBlank()) {
            throw new BadRequestException("Store ID must not be blank");
        }
        Store existing = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Store not found with ID: " + storeId));
        existing.setStorName(updatedStore.getStorName());
        existing.setStorAddress(updatedStore.getStorAddress());
        existing.setCity(updatedStore.getCity());
        existing.setState(updatedStore.getState());
        existing.setZip(updatedStore.getZip());
        return storeRepository.save(existing);
    }

    // GET /api/v1/stores/{storeId}/sales
    @Override
    public List<Sales> getSalesByStore(String storeId) {
        if (storeId == null || storeId.isBlank()) {
            throw new BadRequestException("Store ID must not be blank");
        }
        storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Store not found with ID: " + storeId));
        List<Sales> sales = salesRepository.findByStorId(storeId);
        if (sales.isEmpty()) {
            throw new ResourceNotFoundException(
                "No sales found for store ID: " + storeId);
        }
        return sales;
    }

    // GET /api/v1/stores/{storeId}/discounts
    @Override
    public List<Discounts> getDiscountsByStore(String storeId) {
        if (storeId == null || storeId.isBlank()) {
            throw new BadRequestException("Store ID must not be blank");
        }
        storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Store not found with ID: " + storeId));
        List<Discounts> discounts = discountsRepository.findDiscountsByStoreId(storeId);
        
        if (discounts.isEmpty()) {
            throw new ResourceNotFoundException(
                "No discounts found for store ID: " + storeId);
        }
        return discounts;
    }
}