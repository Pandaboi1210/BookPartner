package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.entity.Discounts;
import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.Store;
import com.sprint.BookPartnerApplication.services.StoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")   // ✅ FIXED BASE PATH
public class StoreController {

    @Autowired
    private StoreService storeService;

    // POST /api/v1/stores
    @PostMapping
    public ResponseEntity<Store> createStore(@Valid @RequestBody Store store) {
        return ResponseEntity.ok(storeService.createStore(store));
    }

    // GET /api/v1/stores
    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        return ResponseEntity.ok(storeService.getAllStores());
    }

    // GET /api/v1/stores/{storeId}
    @GetMapping("/{storeId}")
    public ResponseEntity<Store> getStoreById(@PathVariable String storeId) {
        return ResponseEntity.ok(storeService.getStoreById(storeId));
    }

    // PUT /api/v1/stores/{storeId}
    @PutMapping("/{storeId}")
    public ResponseEntity<Store> updateStore(
            @PathVariable String storeId,
            @Valid @RequestBody Store store) {
        return ResponseEntity.ok(storeService.updateStore(storeId, store));
    }

    // GET /api/v1/stores/{storeId}/sales
    @GetMapping("/{storeId}/sales")
    public ResponseEntity<List<Sales>> getSalesByStore(@PathVariable String storeId) {
        return ResponseEntity.ok(storeService.getSalesByStore(storeId));
    }

    // GET /api/v1/stores/{storeId}/discounts
    @GetMapping("/{storeId}/discounts")
    public ResponseEntity<List<Discounts>> getDiscountsByStore(@PathVariable String storeId) {
        return ResponseEntity.ok(storeService.getDiscountsByStore(storeId));
    }
}