package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.Discounts;
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
                .orElseThrow(() -> new RuntimeException("Discount not found for type: " + discountType));

        discount.setDiscounttype(discountDetails.getDiscounttype());
        discount.setLowqty(discountDetails.getLowqty());
        discount.setHighqty(discountDetails.getHighqty());
        discount.setDiscount(discountDetails.getDiscount());
        discount.setStore(discountDetails.getStore());

        return discountsRepository.save(discount);
    }
}