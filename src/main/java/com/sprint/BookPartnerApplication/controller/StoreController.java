package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.StoreRequestDTO;
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
@RequestMapping("/api/v1/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    // ✅ CREATE (DTO)
    @PostMapping
    public ResponseEntity<Store> createStore(@Valid @RequestBody StoreRequestDTO dto) {
        return ResponseEntity.ok(storeService.createStore(dto));
    }

    // ✅ GET ALL
    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        return ResponseEntity.ok(storeService.getAllStores());
    }

    // ✅ GET BY ID
    @GetMapping("/{storeId}")
    public ResponseEntity<Store> getStoreById(@PathVariable String storeId) {
        return ResponseEntity.ok(storeService.getStoreById(storeId));
    }

    // ✅ UPDATE (DTO)
    @PutMapping("/{storeId}")
    public ResponseEntity<Store> updateStore(
            @PathVariable String storeId,
            @Valid @RequestBody StoreRequestDTO dto) {
        return ResponseEntity.ok(storeService.updateStore(storeId, dto));
    }

    // ✅ GET SALES
    @GetMapping("/{storeId}/sales")
    public ResponseEntity<List<Sales>> getSalesByStore(@PathVariable String storeId) {
        return ResponseEntity.ok(storeService.getSalesByStore(storeId));
    }

    // ✅ GET DISCOUNTS
    @GetMapping("/{storeId}/discounts")
    public ResponseEntity<List<Discounts>> getDiscountsByStore(@PathVariable String storeId) {
        return ResponseEntity.ok(storeService.getDiscountsByStore(storeId));
    }
}