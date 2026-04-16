package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.entity.Store;
import com.sprint.BookPartnerApplication.servicesImpl.StoreServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stores")
public class StoreController {

    @Autowired
    private StoreServiceImpl storeService;

    @GetMapping
    public List<Store> getAllStores() {
        return storeService.getAllStores();
    }

    @GetMapping("/{storId}")
    public Optional<Store> getStoreById(@PathVariable String storId) {
        return storeService.getStoreById(storId);
    }

    @PostMapping
    public Store createStore(@RequestBody Store store) {
        return storeService.saveStore(store);
    }

    @PutMapping("/{storId}")
    public Store updateStore(@PathVariable String storId, @RequestBody Store store) {
        store.setStorId(storId);
        return storeService.saveStore(store);
    }

    @DeleteMapping("/{storId}")
    public void deleteStore(@PathVariable String storId) {
        storeService.deleteStore(storId);
    }
}