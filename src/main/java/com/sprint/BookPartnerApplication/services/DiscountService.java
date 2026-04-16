package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.Discounts;

import java.util.List;

public interface DiscountService {

    List<Discounts> getAllDiscounts();

    Discounts createDiscount(Discounts discount);

    List<Discounts> getDiscountsByStore(String storeId);

    Discounts updateDiscountByType(String discountType, Discounts discount);
}