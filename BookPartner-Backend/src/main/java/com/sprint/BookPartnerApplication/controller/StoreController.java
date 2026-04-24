package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.StoreRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.SalesResponseDTO;
import com.sprint.BookPartnerApplication.dto.response.StoreResponseDTO;
import com.sprint.BookPartnerApplication.entity.Discounts;
import com.sprint.BookPartnerApplication.services.StoreService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    
    @PostMapping
    public ResponseEntity<StoreResponseDTO> createStore(
            @Valid @RequestBody StoreRequestDTO dto) {

        StoreResponseDTO data = storeService.createStore(dto);

        return created(data);
    }

   
    @GetMapping
    public ResponseEntity<List<StoreResponseDTO>> getAllStores() {

        List<StoreResponseDTO> data = storeService.getAllStores();

        return ok(data);
    }

    
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponseDTO> getStoreById(
            @PathVariable String storeId) {

        StoreResponseDTO data = storeService.getStoreById(storeId);

        return ok(data);
    }

    
    @PutMapping("/{storeId}")
    public ResponseEntity<StoreResponseDTO> updateStore(
            @PathVariable String storeId,
            @Valid @RequestBody StoreRequestDTO dto) {

        System.out.println("Controller storeId = " + storeId);

        StoreResponseDTO data =
                storeService.updateStore(storeId, dto);

        return ok(data);
    }

   
    @GetMapping("/{storeId}/sales")
    public ResponseEntity<List<SalesResponseDTO>> getSalesByStore(
            @PathVariable String storeId) {

        List<SalesResponseDTO> data =
                storeService.getSalesByStore(storeId);

        return ok(data);
    }

    
    @GetMapping("/{storeId}/discounts")
    public ResponseEntity<List<Discounts>> getDiscountsByStore(
            @PathVariable String storeId) {

        List<Discounts> data =
                storeService.getDiscountsByStore(storeId);

        return ok(data);
    }

    

    private <T> ResponseEntity<T> ok(T data) {
        return ResponseEntity.ok(data);
    }

    private <T> ResponseEntity<T> created(T data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(data);
    }
}