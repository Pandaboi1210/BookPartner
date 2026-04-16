package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.Sales;

import com.sprint.BookPartnerApplication.exception.BadRequestException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;

import com.sprint.BookPartnerApplication.repository.SalesRepository;
import com.sprint.BookPartnerApplication.repository.StoreRepository;
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

    // POST /api/v1/sales
    @Override
    public Sales createSale(Sales sale) {
        if (sale.getStorId() == null || sale.getStorId().isBlank()) {
            throw new BadRequestException("Store ID must not be blank");
        }
        if (sale.getTitleId() == null || sale.getTitleId().isBlank()) {
            throw new BadRequestException("Title ID must not be blank");
        }
        storeRepository.findById(sale.getStorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Store not found with ID: " + sale.getStorId()));
        return salesRepository.save(sale);
    }

    // GET /api/v1/sales
    @Override
    public List<Sales> getAllSales() {
        List<Sales> sales = salesRepository.findAll();
        if (sales.isEmpty()) {
            throw new ResourceNotFoundException("No sales records found");
        }
        return sales;
    }

    // GET /api/v1/sales/store/{storeId}
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

    // GET /api/v1/sales/title/{titleId}
    @Override
    public List<Sales> getSalesByTitle(String titleId) {
        if (titleId == null || titleId.isBlank()) {
            throw new BadRequestException("Title ID must not be blank");
        }
        List<Sales> sales = salesRepository.findByTitleId(titleId);
        if (sales.isEmpty()) {
            throw new ResourceNotFoundException(
                "No sales found for title ID: " + titleId);
        }
        return sales;
    }

    // GET /api/v1/sales/date-range?from=&to=
    @Override
    public List<Sales> getSalesByDateRange(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null) {
            throw new BadRequestException("Both 'from' and 'to' dates are required");
        }
        if (from.isAfter(to)) {
            throw new BadRequestException(
                "'from' date must not be after 'to' date");
        }
        List<Sales> sales = salesRepository.findByOrdDateBetween(from, to);
        if (sales.isEmpty()) {
            throw new ResourceNotFoundException(
                "No sales found between " + from + " and " + to);
        }
        return sales;
    }
}