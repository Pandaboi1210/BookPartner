package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.dto.request.StoreRequestDTO;
import com.sprint.BookPartnerApplication.entity.Discounts;
import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.Store;
import com.sprint.BookPartnerApplication.exception.BadRequestException;
import com.sprint.BookPartnerApplication.exception.DuplicateResourceException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
import com.sprint.BookPartnerApplication.repository.DiscountRepository;
import com.sprint.BookPartnerApplication.repository.SalesRepository;
import com.sprint.BookPartnerApplication.repository.StoreRepository;
import com.sprint.BookPartnerApplication.services.StoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private DiscountRepository discountsRepository;

    // ✅ DTO → Entity Mapping
    private Store mapToEntity(StoreRequestDTO dto) {
        Store store = new Store();
        store.setStorId(dto.getStorId());
        store.setStorName(dto.getStorName());
        store.setStorAddress(dto.getStorAddress());
        store.setCity(dto.getCity());
        store.setState(dto.getState());
        store.setZip(dto.getZip());
        return store;
    }

    // ✅ CREATE
    @Override
    public Store createStore(StoreRequestDTO dto) {

        if (dto.getStorId() == null || dto.getStorId().isBlank()) {
            throw new BadRequestException("Store ID must not be blank");
        }

        if (storeRepository.existsById(dto.getStorId())) {
            throw new DuplicateResourceException(
                    "Store already exists with ID: " + dto.getStorId());
        }

        Store store = mapToEntity(dto);
        return storeRepository.save(store);
    }

    // ✅ GET ALL
    @Override
    public List<Store> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        if (stores.isEmpty()) {
            throw new ResourceNotFoundException("No stores found");
        }
        return stores;
    }

    // ✅ GET BY ID
    @Override
    public Store getStoreById(String storeId) {

        if (storeId == null || storeId.isBlank()) {
            throw new BadRequestException("Store ID must not be blank");
        }

        return storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Store not found with ID: " + storeId));
    }

    // ✅ UPDATE
    @Override
    public Store updateStore(String storeId, StoreRequestDTO dto) {

        if (storeId == null || storeId.isBlank()) {
            throw new BadRequestException("Store ID must not be blank");
        }

        Store existing = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Store not found with ID: " + storeId));

        existing.setStorName(dto.getStorName());
        existing.setStorAddress(dto.getStorAddress());
        existing.setCity(dto.getCity());
        existing.setState(dto.getState());
        existing.setZip(dto.getZip());

        return storeRepository.save(existing);
    }

    // ✅ GET SALES
    @Override
    public List<Sales> getSalesByStore(String storeId) {

        if (storeId == null || storeId.isBlank()) {
            throw new BadRequestException("Store ID must not be blank");
        }

        storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Store not found with ID: " + storeId));

        List<Sales> sales = salesRepository.findByStorId(storeId);

        if (sales.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No sales found for store ID: " + storeId);
        }

        return sales;
    }

    // ✅ GET DISCOUNTS
    @Override
    public List<Discounts> getDiscountsByStore(String storeId) {

        if (storeId == null || storeId.isBlank()) {
            throw new BadRequestException("Store ID must not be blank");
        }

        storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Store not found with ID: " + storeId));

        List<Discounts> discounts =
                discountsRepository.findDiscountsByStoreId(storeId);

        if (discounts.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No discounts found for store ID: " + storeId);
        }

        return discounts;
    }
}