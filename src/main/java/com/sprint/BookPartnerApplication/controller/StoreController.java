package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.StoreRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.SalesResponseDTO;
import com.sprint.BookPartnerApplication.dto.response.StoreResponseDTO;
import com.sprint.BookPartnerApplication.entity.Discounts;
import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.Store;
import com.sprint.BookPartnerApplication.services.StoreService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @PostMapping
    public ResponseEntity<StoreResponseDTO> createStore(@Valid @RequestBody StoreRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.createStore(dto));
    }

    @GetMapping
    public ResponseEntity<List<StoreResponseDTO>> getAllStores() {
        return ResponseEntity.ok(storeService.getAllStores());
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponseDTO> getStoreById(@PathVariable String storeId) {
        return ResponseEntity.ok(storeService.getStoreById(storeId));
    }

    @PutMapping("/{storeId}")
    public ResponseEntity<StoreResponseDTO> updateStore(
            @PathVariable String storeId,
            @Valid @RequestBody StoreRequestDTO dto) {
        return ResponseEntity.ok(storeService.updateStore(storeId, dto));
    }

    @GetMapping("/{storeId}/sales")
    public ResponseEntity<List<SalesResponseDTO>> getSalesByStore(@PathVariable String storeId) {
        return ResponseEntity.ok(storeService.getSalesByStore(storeId));
    }

    @GetMapping("/{storeId}/discounts")
    public ResponseEntity<List<Discounts>> getDiscountsByStore(@PathVariable String storeId) {
        return ResponseEntity.ok(storeService.getDiscountsByStore(storeId));
    }
}