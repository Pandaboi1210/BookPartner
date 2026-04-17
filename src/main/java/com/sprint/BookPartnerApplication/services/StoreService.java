package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.response.SalesResponseDTO;
import com.sprint.BookPartnerApplication.dto.request.StoreRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.StoreResponseDTO;
import com.sprint.BookPartnerApplication.entity.Discounts;
import java.util.List;

public interface StoreService {

    StoreResponseDTO createStore(StoreRequestDTO dto);
    List<StoreResponseDTO> getAllStores();
    StoreResponseDTO getStoreById(String storeId);
    StoreResponseDTO updateStore(String storeId, StoreRequestDTO dto);
    List<SalesResponseDTO> getSalesByStore(String storeId);
    List<Discounts> getDiscountsByStore(String storeId);
}