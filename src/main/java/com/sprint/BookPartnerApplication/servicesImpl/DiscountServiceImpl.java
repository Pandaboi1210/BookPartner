package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.Discounts;
import com.sprint.BookPartnerApplication.exception.DuplicateResourceException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
import com.sprint.BookPartnerApplication.repository.DiscountRepository;
import com.sprint.BookPartnerApplication.services.DiscountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountsRepository;

    public DiscountServiceImpl(DiscountRepository discountsRepository) {
        this.discountsRepository = discountsRepository;
    }

    @Override
    public List<Discounts> getAllDiscounts() {
        return discountsRepository.findAll();
    }

    @Override
    public Discounts createDiscount(Discounts discount) {
        // FIXED: only block duplicate type for the SAME store.
        // The same discount type (e.g. "Volume Discount") is valid across different stores.
        if (discount.getDiscounttype() != null && discount.getStore() != null) {
            List<Discounts> existing = discountsRepository.findByDiscounttype(discount.getDiscounttype());
            boolean sameStoreExists = existing.stream()
                    .anyMatch(d -> d.getStore() != null
                            && d.getStore().getStorId().equals(discount.getStore().getStorId()));
            if (sameStoreExists) {
                throw new DuplicateResourceException("Discount type '" + discount.getDiscounttype()
                        + "' already exists for this store.");
            }
        }
        return discountsRepository.save(discount);
    }

    @Override
    public List<Discounts> getDiscountsByStore(String storeId) {
        return discountsRepository.findByStore_StorId(storeId);
    }

    @Override
    public Discounts updateDiscountByType(String discountType, Discounts discountDetails) {
        Discounts discount = discountsRepository.findByDiscounttype(discountType)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found for type: " + discountType));

        discount.setDiscounttype(discountDetails.getDiscounttype());
        discount.setLowqty(discountDetails.getLowqty());
        discount.setHighqty(discountDetails.getHighqty());
        discount.setDiscount(discountDetails.getDiscount());
        discount.setStore(discountDetails.getStore());

        return discountsRepository.save(discount);
    }
}