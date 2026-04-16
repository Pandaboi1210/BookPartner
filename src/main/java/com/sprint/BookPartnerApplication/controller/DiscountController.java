package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.entity.Discounts;
import com.sprint.BookPartnerApplication.services.DiscountService;

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
    public Discounts createDiscount(@RequestBody Discounts discount) {
        return discountService.createDiscount(discount);
    }

    // GET /api/v1/discounts - Get all discounts
    @GetMapping
    public List<Discounts> getAllDiscounts() {
        return discountService.getAllDiscounts();
    }

    // GET /api/v1/discounts/store/{storeId} - Get discounts for a store
    @GetMapping("/store/{storeId}")
    public List<Discounts> getDiscountsByStore(@PathVariable String storeId) {
        return discountService.getDiscountsByStore(storeId);
    }

    // PUT /api/v1/discounts/{discountType} - Update discount
    @PutMapping("/{discountType}")
    public Discounts updateDiscountByType(@PathVariable String discountType, @RequestBody Discounts discount) {
        return discountService.updateDiscountByType(discountType, discount);
    }
}