package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.dto.request.SalesRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.SalesResponseDTO;
import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.SalesId;
import com.sprint.BookPartnerApplication.exception.BadRequestException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
import com.sprint.BookPartnerApplication.repository.SalesRepository;
import com.sprint.BookPartnerApplication.repository.StoreRepository;
import com.sprint.BookPartnerApplication.repository.TitleRepository;
import com.sprint.BookPartnerApplication.services.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private TitleRepository titleRepository;

    private SalesResponseDTO mapToResponseDTO(Sales s) {
        SalesResponseDTO dto = new SalesResponseDTO();
        dto.setStorId(s.getStorId());
        dto.setOrdNum(s.getOrdNum());
        dto.setTitleId(s.getTitleId());
        dto.setOrdDate(s.getOrdDate());
        dto.setQty(s.getQty());
        dto.setPayterms(s.getPayterms());

        if (s.getStore() != null) {
            dto.setStoreName(s.getStore().getStorName());
        } else {
            storeRepository.findById(s.getStorId())
                    .ifPresent(store -> dto.setStoreName(store.getStorName()));
        }

        if (s.getTitle() != null) {
            dto.setTitleName(s.getTitle().getTitle());
        } else {
            titleRepository.findById(s.getTitleId())
                    .ifPresent(title -> dto.setTitleName(title.getTitle()));
        }

        return dto;
    }

    @Override
    @Transactional
    public SalesResponseDTO createSale(SalesRequestDTO dto) {

        if (dto.getStorId() == null || dto.getStorId().isBlank()) {
            throw new BadRequestException("Store ID must not be blank");
        }
        if (dto.getTitleId() == null || dto.getTitleId().isBlank()) {
            throw new BadRequestException("Title ID must not be blank");
        }
        if (dto.getOrdNum() == null || dto.getOrdNum().isBlank()) {
            throw new BadRequestException("Order Number must not be blank");
        }

        SalesId id = new SalesId(dto.getStorId(), dto.getOrdNum(), dto.getTitleId());
        if (salesRepository.existsById(id)) {
            throw new BadRequestException("Sale already exists with this Store+Order+Title combination");
        }

        storeRepository.findById(dto.getStorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Store not found with ID: " + dto.getStorId()));

        titleRepository.findById(dto.getTitleId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Title not found with ID: " + dto.getTitleId()));

        // ✅ Build entity using ONLY primitive fields — do NOT set store/title objects
        // Setting both storId column AND store relationship causes duplicate column conflict
        Sales sale = new Sales();
        sale.setStorId(dto.getStorId());
        sale.setOrdNum(dto.getOrdNum());
        sale.setTitleId(dto.getTitleId());
        sale.setOrdDate(dto.getOrdDate());
        sale.setQty(dto.getQty());
        sale.setPayterms(dto.getPayterms());
        // ✅ Do NOT call sale.setStore() or sale.setTitle() here
        // The @JoinColumn uses insertable=false,updatable=false (fix in entity below)

        salesRepository.save(sale);

        // ✅ Reload with JOIN FETCH so store and title relationships are populated
        List<Sales> salesList = salesRepository.findByStorIdWithDetails(dto.getStorId());

        Sales fullData = salesList.stream()
                .filter(s -> s.getOrdNum().equals(dto.getOrdNum())
                        && s.getTitleId().equals(dto.getTitleId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Saved sale could not be reloaded"));

        return mapToResponseDTO(fullData);
    }

    @Override
    public List<SalesResponseDTO> getAllSales() {
        List<Sales> sales = salesRepository.findAllWithDetails();
        if (sales.isEmpty()) {
            throw new ResourceNotFoundException("No sales records found");
        }
        return sales.stream().map(this::mapToResponseDTO).toList();
    }

    @Override
    public List<SalesResponseDTO> getSalesByStore(String storeId) {
        if (storeId == null || storeId.isBlank()) {
            throw new BadRequestException("Store ID must not be blank");
        }
        storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Store not found with ID: " + storeId));
        List<Sales> sales = salesRepository.findByStorIdWithDetails(storeId);
        if (sales.isEmpty()) {
            throw new ResourceNotFoundException("No sales found for store ID: " + storeId);
        }
        return sales.stream().map(this::mapToResponseDTO).toList();
    }

    @Override
    public List<SalesResponseDTO> getSalesByTitle(String titleId) {
        if (titleId == null || titleId.isBlank()) {
            throw new BadRequestException("Title ID must not be blank");
        }
        List<Sales> sales = salesRepository.findByTitleId(titleId);
        if (sales.isEmpty()) {
            throw new ResourceNotFoundException("No sales found for title ID: " + titleId);
        }
        return sales.stream().map(this::mapToResponseDTO).toList();
    }

    @Override
    public List<SalesResponseDTO> getSalesByDateRange(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null) {
            throw new BadRequestException("Both 'from' and 'to' dates are required");
        }
        if (from.isAfter(to)) {
            throw new BadRequestException("'from' date must not be after 'to'");
        }
        List<Sales> sales = salesRepository.findByOrdDateBetween(from, to);
        if (sales.isEmpty()) {
            throw new ResourceNotFoundException("No sales found between given dates");
        }
        return sales.stream().map(this::mapToResponseDTO).toList();
    }
}