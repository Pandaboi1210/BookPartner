package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.SalesId; 
import com.sprint.BookPartnerApplication.exception.BadRequestException;
import com.sprint.BookPartnerApplication.exception.DuplicateResourceException; 
import com.sprint.BookPartnerApplication.exception.InvalidInputException;
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

    // POST /api/v1/sales
    @Override
    public Sales createSale(Sales sale) {
        if (sale.getStorId() == null || sale.getStorId().isBlank()) {
            throw new BadRequestException("Store ID must not be blank");
        }
        if (sale.getTitleId() == null || sale.getTitleId().isBlank()) {
            throw new BadRequestException("Title ID must not be blank");
        }
        if (sale.getOrdNum() == null || sale.getOrdNum().isBlank()) {
            throw new BadRequestException("Order Number must not be blank");
        }
        
        // 🚨 409 CONFLICT Check: Does this exact sale record already exist?
        SalesId id = new SalesId(sale.getStorId(), sale.getOrdNum(), sale.getTitleId());
        if (salesRepository.existsById(id)) {
            throw new DuplicateResourceException("Sale record already exists for this Store, Order Number, and Title.");
        }
        
        // 🚨 404 Check: Does the Store exist?
        storeRepository.findById(sale.getStorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Store not found with ID: " + sale.getStorId()));
                        
        // 🚨 404 Check: Does the Title exist?
        if (!titleRepository.existsById(sale.getTitleId())) {
            throw new ResourceNotFoundException("Title not found with ID: " + sale.getTitleId());
        }
        
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
        
        // 🚨 400 INVALID INPUT: Business rule validation
        if (from.isAfter(to)) {
            throw new InvalidInputException("'from' date must not be after 'to' date");
        }
        
        List<Sales> sales = salesRepository.findByOrdDateBetween(from, to);
        if (sales.isEmpty()) {
            throw new ResourceNotFoundException(
                "No sales found between " + from + " and " + to);
        }
        return sales;
    }
}