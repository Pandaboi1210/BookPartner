package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.Sales;
import java.time.LocalDateTime;
import java.util.List;

public interface SalesService {

    // POST /api/v1/sales
    Sales createSale(Sales sale);

    // GET /api/v1/sales
    List<Sales> getAllSales();

    // GET /api/v1/sales/store/{storeId}
    List<Sales> getSalesByStore(String storeId);

    // GET /api/v1/sales/title/{titleId}
    List<Sales> getSalesByTitle(String titleId);

    // GET /api/v1/sales/date-range?from=&to=
    List<Sales> getSalesByDateRange(LocalDateTime from, LocalDateTime to);
}