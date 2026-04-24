package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.dto.request.DiscountRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.DiscountResponseDTO;
import com.sprint.BookPartnerApplication.entity.Discounts;
import com.sprint.BookPartnerApplication.entity.Store;
import com.sprint.BookPartnerApplication.exception.DuplicateResourceException;
import com.sprint.BookPartnerApplication.exception.InvalidInputException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
import com.sprint.BookPartnerApplication.repository.DiscountRepository;
import com.sprint.BookPartnerApplication.repository.StoreRepository;
import com.sprint.BookPartnerApplication.services.DiscountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountsRepository;
    private final StoreRepository storeRepository;

    public DiscountServiceImpl(DiscountRepository discountsRepository,
                               StoreRepository storeRepository) {
        this.discountsRepository = discountsRepository;
        this.storeRepository = storeRepository;
    }

    // ── mapping helpers ──────────────────────────────────────────────────────

    private DiscountResponseDTO toResponse(Discounts d) {
        DiscountResponseDTO dto = new DiscountResponseDTO();
        dto.setDiscountId(d.getDiscountId());
        dto.setDiscounttype(d.getDiscounttype());
        dto.setLowqty(d.getLowqty());
        dto.setHighqty(d.getHighqty());
        dto.setDiscount(d.getDiscount());
        if (d.getStore() != null) {
            dto.setStorId(d.getStore().getStorId());
            dto.setStoreName(d.getStore().getStorName());
        }
        return dto;
    }

    private Store resolveStore(String storId) {
        if (storId == null || storId.isBlank()) return null;
        return storeRepository.findById(storId)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with ID: " + storId));
    }

    // ── service methods ───────────────────────────────────────────────────────

    @Override
    public List<DiscountResponseDTO> getAllDiscounts() {
        return discountsRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DiscountResponseDTO> getDiscountsFiltered(
            String storeId,
            String type,
            Integer minLowQty,
            Integer maxHighQty,
            BigDecimal minDiscount,
            BigDecimal maxDiscount
    ) {
        if (minDiscount != null && maxDiscount != null && minDiscount.compareTo(maxDiscount) > 0) {
            throw new InvalidInputException("Minimum discount cannot be greater than maximum discount.");
        }

        String normalizedStoreId = storeId == null ? null : storeId.trim();
        String normalizedType = type == null ? null : type.trim();

        if (normalizedStoreId != null && !normalizedStoreId.isBlank()) {
            resolveStore(normalizedStoreId);
        }

        return discountsRepository.findAll()
                .stream()
                .filter(d -> {
                    if (normalizedStoreId == null || normalizedStoreId.isBlank()) return true;
                    return d.getStore() != null && normalizedStoreId.equalsIgnoreCase(d.getStore().getStorId());
                })
                .filter(d -> {
                    if (normalizedType == null || normalizedType.isBlank()) return true;
                    String dt = d.getDiscounttype() == null ? "" : d.getDiscounttype();
                    return dt.toLowerCase().contains(normalizedType.toLowerCase());
                })
                .filter(d -> {
                    if (minLowQty == null) return true;
                    return d.getLowqty() != null && d.getLowqty() >= minLowQty;
                })
                .filter(d -> {
                    if (maxHighQty == null) return true;
                    return d.getHighqty() != null && d.getHighqty() <= maxHighQty;
                })
                .filter(d -> {
                    if (minDiscount == null) return true;
                    return d.getDiscount() != null && d.getDiscount().compareTo(minDiscount) >= 0;
                })
                .filter(d -> {
                    if (maxDiscount == null) return true;
                    return d.getDiscount() != null && d.getDiscount().compareTo(maxDiscount) <= 0;
                })
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DiscountResponseDTO createDiscount(DiscountRequestDTO requestDTO) {

        // Block duplicate type for the SAME store
        if (requestDTO.getDiscounttype() != null && requestDTO.getStorId() != null) {
            List<Discounts> existing = discountsRepository.findDiscountsByType(requestDTO.getDiscounttype());
            boolean sameStoreExists = existing.stream()
                    .anyMatch(d -> d.getStore() != null
                            && d.getStore().getStorId().equals(requestDTO.getStorId()));
            if (sameStoreExists) {
                throw new DuplicateResourceException("Discount type '" + requestDTO.getDiscounttype()
                        + "' already exists for this store.");
            }
        }

        Discounts discount = new Discounts();
        discount.setDiscounttype(requestDTO.getDiscounttype());
        discount.setLowqty(requestDTO.getLowqty());
        discount.setHighqty(requestDTO.getHighqty());
        discount.setDiscount(requestDTO.getDiscount());
        discount.setStore(resolveStore(requestDTO.getStorId()));

        return toResponse(discountsRepository.save(discount));
    }

    @Override
    public List<DiscountResponseDTO> getDiscountsByStore(String storeId) {
        return discountsRepository.findDiscountsByStoreId(storeId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DiscountResponseDTO updateDiscountByType(String discountType, DiscountRequestDTO requestDTO) {

        Discounts discount = discountsRepository.findDiscountsByType(discountType)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Discount not found for type: " + discountType));

        discount.setDiscounttype(requestDTO.getDiscounttype());
        discount.setLowqty(requestDTO.getLowqty());
        discount.setHighqty(requestDTO.getHighqty());
        discount.setDiscount(requestDTO.getDiscount());
        discount.setStore(resolveStore(requestDTO.getStorId()));

        return toResponse(discountsRepository.save(discount));
    }
}