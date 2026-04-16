package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.Store;
import com.sprint.BookPartnerApplication.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Optional<Store> getStoreById(String storId) {
        return storeRepository.findById(storId);
    }
    public Store saveStore(Store store) {

        System.out.println("===== DEBUG START =====");
        System.out.println("storId received: " + store.getStorId());
        System.out.println("===== DEBUG END =====");

        return storeRepository.save(store);
    }

    public void deleteStore(String storId) {
        storeRepository.deleteById(storId);
    }
}