package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.Discounts;
import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.Store;
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
    private DiscountRepository discountsRepository;  // ← NEW

    // POST /api/v1/stores
    @Override
    public Store createStore(Store store) {
        return storeRepository.save(store);
    }

    // GET /api/v1/stores
    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    // GET /api/v1/stores/{storeId}
    @Override
    public Store getStoreById(String storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException(
                    "Store not found with ID: " + storeId));
    }

    // PUT /api/v1/stores/{storeId}
    @Override
    public Store updateStore(String storeId, Store updatedStore) {
        Store existing = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException(
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
        storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException(
                    "Store not found with ID: " + storeId));
        return salesRepository.findByStorId(storeId);
    }

    // GET /api/v1/stores/{storeId}/discounts  ← NEW
    @Override
    public List<Discounts> getDiscountsByStore(String storeId) {
        storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException(
                    "Store not found with ID: " + storeId));
        return discountsRepository.findByStore_StorId(storeId);
    }
}