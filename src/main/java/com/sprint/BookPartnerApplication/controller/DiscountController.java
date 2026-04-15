package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.entity.Discounts;
import com.sprint.BookPartnerApplication.services.DiscountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/discounts")
public class DiscountController {

    private final DiscountService discountsService;

    public DiscountController(DiscountService discountsService) {
        this.discountsService = discountsService;
    }

    @GetMapping
    public List<Discounts> getAllDiscounts() {
        return discountsService.getAllDiscounts();
    }

    @GetMapping("/{id}")
    public Optional<Discounts> getDiscountById(@PathVariable Integer id) {
        return discountsService.getDiscountById(id);
    }

    @PostMapping
    public Discounts createDiscount(@RequestBody Discounts discount) {
        return discountsService.createDiscount(discount);
    }

    @PutMapping("/{id}")
    public Discounts updateDiscount(
            @PathVariable Integer id,
            @RequestBody Discounts discount) {
        return discountsService.updateDiscount(id, discount);
    }

     @DeleteMapping("/{id}")
    public void deleteDiscount(@PathVariable Integer id) {
        discountsService.deleteDiscount(id);
    }
}