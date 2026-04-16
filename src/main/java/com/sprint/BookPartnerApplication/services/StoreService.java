package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.Discounts;
import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.Store;
import java.util.List;

public interface StoreService {

    // POST /api/v1/stores
    Store createStore(Store store);

    // GET /api/v1/stores
    List<Store> getAllStores();

    // GET /api/v1/stores/{storeId}
    Store getStoreById(String storeId);

    // PUT /api/v1/stores/{storeId}
    Store updateStore(String storeId, Store store);

    // GET /api/v1/stores/{storeId}/sales
    List<Sales> getSalesByStore(String storeId);

    // GET /api/v1/stores/{storeId}/discounts  ← NEW
    List<Discounts> getDiscountsByStore(String storeId);
}