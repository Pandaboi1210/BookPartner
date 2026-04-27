package com.sprint.BookPartnerApplication.testinsert;

import java.math.BigDecimal;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sprint.BookPartnerApplication.dto.request.DiscountRequestDTO;
import com.sprint.BookPartnerApplication.entity.Store;
import com.sprint.BookPartnerApplication.repository.DiscountRepository;
import com.sprint.BookPartnerApplication.repository.StoreRepository;
import com.sprint.BookPartnerApplication.services.DiscountService;

import java.util.List;

@SpringBootTest
@Order(7)
public class DiscountDataInsertTest {

    @Autowired private DiscountService discountsService;
    @Autowired private DiscountRepository discountRepository;
    @Autowired private StoreRepository storeRepository;

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
    public void insertDiscounts() {
        long currentCount = discountRepository.count();
        if (currentCount >= 10) {
            System.out.println("Already have " + currentCount + " discounts. Skipping insertion.");
            return;
        }

        List<Store> stores = storeRepository.findAll();
        if (stores.isEmpty()) {
            System.out.println("No stores found. Cannot insert discounts with store links. Please run StoreDataInsertTest first.");
            return;
        }

        String[] types = {
            "Initial Customer", "Volume Discount", "Customer Discount",
            "Seasonal Promo", "Holiday Special", "Bulk Buy",
            "Loyalty Reward", "New Store Opening", "Clearance Sale", "Flash Deal"
        };
        double[] values = {10.5, 6.7, 5.0, 15.0, 12.0, 8.5, 7.0, 20.0, 25.0, 30.0};

        int typeIndex = 0;
        int storeIndex = 0;
        int attempts = 0;
        int maxAttempts = 50; // Safety break

        while (discountRepository.count() < 10 && attempts < maxAttempts) {
            String type = types[typeIndex % types.length];
            Store store = stores.get(storeIndex % stores.size());

            // Check if this type already exists for this store to prevent duplicates
            boolean exists = discountRepository.findDiscountsByType(type)
                    .stream()
                    .anyMatch(d -> d.getStore() != null && d.getStore().getStorId().equals(store.getStorId()));

            if (!exists) {
                insertDiscount(type, store.getStorId(), (typeIndex + 1) * 5, (typeIndex + 2) * 10, values[typeIndex % values.length]);
            }

            typeIndex++;
            storeIndex++;
            attempts++;
        }

        System.out.println("Discounts insertion process completed. Total count: " + discountRepository.count());
    }
}
