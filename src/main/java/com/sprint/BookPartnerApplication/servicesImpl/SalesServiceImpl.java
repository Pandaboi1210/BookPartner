package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.SalesId;
import com.sprint.BookPartnerApplication.repository.SalesRepository;
import com.sprint.BookPartnerApplication.services.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesRepository salesRepository;

    // POST /api/v1/sales - Create a sales record
    @Override
    public Sales createSale(Sales sale) {
        return salesRepository.save(sale);
    }

    // GET /api/v1/sales - Retrieve all sales
    @Override
    public List<Sales> getAllSales() {
        return salesRepository.findAll();
    }

    // GET /api/v1/sales/store/{storeId} - Get sales by store
    @Override
    public List<Sales> getSalesByStore(String storeId) {
        return salesRepository.findByStorId(storeId);
    }

    // GET /api/v1/sales/title/{titleId} - Get sales by title
    @Override
    public List<Sales> getSalesByTitle(String titleId) {
        return salesRepository.findByTitleId(titleId);
    }

    // GET /api/v1/sales/date-range?from=&to= - Get sales between dates
    @Override
    public List<Sales> getSalesByDateRange(LocalDateTime from, LocalDateTime to) {
        return salesRepository.findByOrdDateBetween(from, to);
    }
}