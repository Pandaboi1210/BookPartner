package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.dto.response.SalesResponseDTO;
import com.sprint.BookPartnerApplication.dto.request.StoreRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.StoreResponseDTO;
import com.sprint.BookPartnerApplication.entity.Discounts;
import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.Store;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.exception.BadRequestException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
import com.sprint.BookPartnerApplication.repository.DiscountRepository;
import com.sprint.BookPartnerApplication.repository.SalesRepository;
import com.sprint.BookPartnerApplication.repository.StoreRepository;
import com.sprint.BookPartnerApplication.repository.TitleRepository;
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

    @Autowired
    private TitleRepository titleRepository;

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

    private StoreResponseDTO mapToResponseDTO(Store store) {
        StoreResponseDTO dto = new StoreResponseDTO();
        dto.setStorId(store.getStorId());
        dto.setStorName(store.getStorName());
        dto.setStorAddress(store.getStorAddress());
        dto.setCity(store.getCity());
        dto.setState(store.getState());
        dto.setZip(store.getZip());

        if (store.getSales() != null) {
            dto.setTotalSales(store.getSales().size());
        }

        return dto;
    }

    // ✅ Reusable method to map Sales entity → SalesResponseDTO with storeName + titleName
    private SalesResponseDTO mapSaleToResponseDTO(Sales s) {
        SalesResponseDTO dto = new SalesResponseDTO();
        dto.setStorId(s.getStorId());
        dto.setOrdNum(s.getOrdNum());
        dto.setTitleId(s.getTitleId());
        dto.setOrdDate(s.getOrdDate());
        dto.setQty(s.getQty());
        dto.setPayterms(s.getPayterms());

        // ✅ Get storeName from the JOIN FETCHed store relationship
        if (s.getStore() != null) {
            dto.setStoreName(s.getStore().getStorName());
        } else {
            // fallback: query directly
            storeRepository.findById(s.getStorId()).ifPresent(store ->
                dto.setStoreName(store.getStorName())
            );
        }

        // ✅ Get titleName from the JOIN FETCHed title relationship
        if (s.getTitle() != null) {
            dto.setTitleName(s.getTitle().getTitle());
        } else {
            // fallback: query directly
            titleRepository.findById(s.getTitleId()).ifPresent(title ->
                dto.setTitleName(title.getTitle())
            );
        }

        return dto;
    }

    @Override
    public StoreResponseDTO createStore(StoreRequestDTO dto) {

        if (dto.getStorId() == null || dto.getStorId().isBlank()) {
            throw new BadRequestException("Store ID must not be blank");
        }

        if (storeRepository.existsById(dto.getStorId())) {
            throw new BadRequestException("Store already exists");
        }

        Store saved = storeRepository.save(mapToEntity(dto));
        return mapToResponseDTO(saved);
    }

    @Override
    public List<StoreResponseDTO> getAllStores() {
        List<Store> stores = storeRepository.findAll();

        if (stores.isEmpty()) {
            throw new ResourceNotFoundException("No stores found");
        }

        return stores.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public StoreResponseDTO getStoreById(String storeId) {

        if (storeId == null || storeId.isBlank()) {
            throw new BadRequestException("Store ID must not be blank");
        }

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Store not found with ID: " + storeId));

        return mapToResponseDTO(store);
    }

    @Override
    public StoreResponseDTO updateStore(String storeId, StoreRequestDTO dto) {

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

        Store updated = storeRepository.save(existing);
        return mapToResponseDTO(updated);
    }

    @Override
    public List<SalesResponseDTO> getSalesByStore(String storeId) {

        if (storeId == null || storeId.isBlank()) {
            throw new BadRequestException("Store ID must not be blank");
        }

        storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Store not found with ID: " + storeId));

        // ✅ Uses JOIN FETCH query so store and title are already loaded
        List<Sales> sales = salesRepository.findByStorIdWithDetails(storeId);

        if (sales.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No sales found for store ID: " + storeId);
        }

        // ✅ Now uses mapSaleToResponseDTO which correctly sets storeName and titleName
        return sales.stream()
                .map(this::mapSaleToResponseDTO)
                .toList();
    }

    @Override
    public List<Discounts> getDiscountsByStore(String storeId) {

        if (storeId == null || storeId.isBlank()) {
            throw new BadRequestException("Store ID must not be blank");
        }

        storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Store not found with ID: " + storeId));

        List<Discounts> discounts = discountsRepository.findDiscountsByStoreId(storeId);

        if (discounts.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No discounts found for store ID: " + storeId);
        }

        return discounts;
    }
}