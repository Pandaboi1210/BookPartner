package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.Discounts;
import com.sprint.BookPartnerApplication.repository.DiscountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountServiceImpl {

    private final DiscountRepository discountsRepository;

    public DiscountServiceImpl(DiscountRepository discountsRepository) {
        this.discountsRepository = discountsRepository;
    }

    public List<Discounts> getAllDiscounts() {
        return discountsRepository.findAll();
    }
    
    public Optional<Discounts> getDiscountById(Integer id) {
        return discountsRepository.findById(id);
    }

    public Discounts createDiscount(Discounts discount) {
        return discountsRepository.save(discount);
    }

    public Discounts updateDiscount(Integer id, Discounts discountDetails) {
        Discounts discount = discountsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found"));

        discount.setDiscounttype(discountDetails.getDiscounttype());
        discount.setLowqty(discountDetails.getLowqty());
        discount.setHighqty(discountDetails.getHighqty());
        discount.setDiscount(discountDetails.getDiscount());
        discount.setStore(discountDetails.getStore());

        return discountsRepository.save(discount);
    }

    public void deleteDiscount(Integer id) {
        discountsRepository.deleteById(id);
    }
}