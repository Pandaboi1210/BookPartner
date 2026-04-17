package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.SalesRequestDTO;
import com.sprint.BookPartnerApplication.entity.Sales;
import java.time.LocalDateTime;
import java.util.List;

public interface SalesService {

    Sales createSale(SalesRequestDTO dto);          // POST
    List<Sales> getAllSales();                       // GET all
    List<Sales> getSalesByStore(String storeId);    // GET by store
    List<Sales> getSalesByTitle(String titleId);    // GET by title
    List<Sales> getSalesByDateRange(LocalDateTime from, LocalDateTime to); // GET by date
}