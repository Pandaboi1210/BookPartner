package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.DiscountRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.DiscountResponseDTO;
import com.sprint.BookPartnerApplication.services.DiscountService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discounts")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    // POST /api/v1/discounts - Create discount
    @PostMapping
    public DiscountResponseDTO createDiscount(@Valid @RequestBody DiscountRequestDTO requestDTO) {
        return discountService.createDiscount(requestDTO);
    }

    // GET /api/v1/discounts - Get all discounts
    @GetMapping
    public List<DiscountResponseDTO> getAllDiscounts() {
        return discountService.getAllDiscounts();
    }

    // GET /api/v1/discounts/store/{storeId} - Get discounts for a store
    @GetMapping("/store/{storeId}")
    public List<DiscountResponseDTO> getDiscountsByStore(@PathVariable String storeId) {
        return discountService.getDiscountsByStore(storeId);
    }

    // PUT /api/v1/discounts/{discountType} - Update discount
    @PutMapping("/{discountType}")
    public DiscountResponseDTO updateDiscountByType(@PathVariable String discountType,
                                                    @Valid @RequestBody DiscountRequestDTO requestDTO) {
        return discountService.updateDiscountByType(discountType, requestDTO);
    }
}