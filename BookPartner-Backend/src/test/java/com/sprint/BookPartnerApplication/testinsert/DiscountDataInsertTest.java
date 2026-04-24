package com.sprint.BookPartnerApplication.testinsert;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.sprint.BookPartnerApplication.dto.request.DiscountRequestDTO;
import com.sprint.BookPartnerApplication.services.DiscountService;

@SpringBootTest
@Order(6)
@Transactional
@Rollback(false)
public class DiscountDataInsertTest {

    @Autowired private DiscountService discountsService;

    @FunctionalInterface
    interface InsertAction { void execute(); }

    private void safeInsert(InsertAction action) {
        try { action.execute(); } catch (Exception e) { /* Already exists, skip */ }
    }

    private void insertDiscount(String type, String storId, Integer lowqty,
                                Integer highqty, double discount) {
        safeInsert(() -> {
            DiscountRequestDTO dto = new DiscountRequestDTO();
            dto.setDiscounttype(type);
            dto.setStorId(storId);
            dto.setLowqty(lowqty);
            dto.setHighqty(highqty);
            dto.setDiscount(BigDecimal.valueOf(discount));
            discountsService.createDiscount(dto);
        });
    }

    @Test
	public
    void insertDiscounts() {
        insertDiscount("Initial Customer",  null,   null, null, 10.5);
        insertDiscount("Volume Discount",   null,    100, 1000,  6.7);
        insertDiscount("Customer Discount", "8042",  null, null,  5.0);
        System.out.println("Discounts inserted.");
    }
}
