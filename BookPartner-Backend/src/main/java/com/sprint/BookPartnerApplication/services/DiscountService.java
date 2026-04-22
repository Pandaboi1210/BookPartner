package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.DiscountRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.DiscountResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface DiscountService {

    List<DiscountResponseDTO> getAllDiscounts();
    List<DiscountResponseDTO> getDiscountsFiltered(
            String storeId,
            String type,
            Integer minLowQty,
            Integer maxHighQty,
            BigDecimal minDiscount,
            BigDecimal maxDiscount
    );

    DiscountResponseDTO createDiscount(DiscountRequestDTO requestDTO);

    List<DiscountResponseDTO> getDiscountsByStore(String storeId);

    DiscountResponseDTO updateDiscountByType(String discountType, DiscountRequestDTO requestDTO);
}