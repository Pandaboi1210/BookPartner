package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.StoreRequestDTO;
import com.sprint.BookPartnerApplication.entity.Discounts;
import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.Store;

import java.util.List;

public interface StoreService {

    Store createStore(StoreRequestDTO dto);

    List<Store> getAllStores();

    Store getStoreById(String storeId);

    Store updateStore(String storeId, StoreRequestDTO dto);

    List<Sales> getSalesByStore(String storeId);

    List<Discounts> getDiscountsByStore(String storeId);
}