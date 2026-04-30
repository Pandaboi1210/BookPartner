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

    // Convert request DTO to entity
    private Sales mapToEntity(SalesRequestDTO dto) {
        Sales sale = new Sales();
        sale.setStorId(dto.getStorId());
        sale.setOrdNum(dto.getOrdNum());
        sale.setTitleId(dto.getTitleId());
        sale.setOrdDate(dto.getOrdDate());
        sale.setQty(dto.getQty());
        sale.setPayterms(dto.getPayterms());
        return sale;
    }

    // Convert entity to response DTO
    private SalesResponseDTO mapToResponseDTO(Sales sale) {
        SalesResponseDTO dto = new SalesResponseDTO();
        dto.setStorId(sale.getStorId());
        dto.setOrdNum(sale.getOrdNum());
        dto.setTitleId(sale.getTitleId());
        dto.setOrdDate(sale.getOrdDate());
        dto.setQty(sale.getQty());
        dto.setPayterms(sale.getPayterms());

        if (sale.getStore() != null) {
            dto.setStoreName(sale.getStore().getStorName());
        }
        if (sale.getTitle() != null) {
            dto.setTitleName(sale.getTitle().getTitle());
        }
        return dto;
    }

    // Create sale
    @Override
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

        // Check duplicate sale
        SalesId id = new SalesId(dto.getStorId(), dto.getOrdNum(), dto.getTitleId());
        if (salesRepository.existsById(id)) {
            throw new BadRequestException("Sale already exists");
        }

        // Check store exists
        storeRepository.findById(dto.getStorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Store not found with ID: " + dto.getStorId()));

        // Check title exists
        if (!titleRepository.existsById(dto.getTitleId())) {
            throw new ResourceNotFoundException(
                    "Title not found with ID: " + dto.getTitleId());
        }

        Sales saved = salesRepository.save(mapToEntity(dto));
        return mapToResponseDTO(saved);
    }

    // Get all sales
    @Override
    public List<SalesResponseDTO> getAllSales() {
        List<Sales> sales = salesRepository.findAll();

        if (sales.isEmpty()) {
            throw new ResourceNotFoundException("No sales records found");
        }

        return sales.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    // Get sales by store
    @Override
    public List<SalesResponseDTO> getSalesByStore(String storeId) {

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

        return sales.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    // Get sales by title
    @Override
    public List<SalesResponseDTO> getSalesByTitle(String titleId) {

        if (titleId == null || titleId.isBlank()) {
            throw new BadRequestException("Title ID must not be blank");
        }

        // Check title exists first
        if (!titleRepository.existsById(titleId)) {
            throw new ResourceNotFoundException(
                    "Title ID does not exist: " + titleId);
        }

        List<Sales> sales = salesRepository.findByTitleId(titleId);

        if (sales.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No sales records found for title ID: " + titleId);
        }

        return sales.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }
    
    // Get sales by date range
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
            throw new ResourceNotFoundException(
                    "No sales found between given dates");
        }

        return sales.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }
}