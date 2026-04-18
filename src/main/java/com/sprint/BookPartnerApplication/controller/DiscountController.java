package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.DiscountRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.DiscountResponseDTO;
import com.sprint.BookPartnerApplication.services.DiscountService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discounts")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<DiscountResponseDTO> createDiscount(
            @Valid @RequestBody DiscountRequestDTO requestDTO) {

        DiscountResponseDTO data =
                discountService.createDiscount(requestDTO);

        return created(data);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<DiscountResponseDTO>> getAllDiscounts() {

        List<DiscountResponseDTO> data =
                discountService.getAllDiscounts();

        return ok(data);
    }

    // GET BY STORE
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<DiscountResponseDTO>> getDiscountsByStore(
            @PathVariable String storeId) {

        List<DiscountResponseDTO> data =
                discountService.getDiscountsByStore(storeId);

        return ok(data);
    }

    // UPDATE
    @PutMapping("/{discountType}")
    public ResponseEntity<DiscountResponseDTO> updateDiscountByType(
            @PathVariable String discountType,
            @Valid @RequestBody DiscountRequestDTO requestDTO) {

        DiscountResponseDTO data =
                discountService.updateDiscountByType(
                        discountType,
                        requestDTO);

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