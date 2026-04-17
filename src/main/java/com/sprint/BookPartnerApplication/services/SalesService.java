package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.SalesRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.SalesResponseDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface SalesService {

    SalesResponseDTO createSale(SalesRequestDTO dto);
    List<SalesResponseDTO> getAllSales();
    List<SalesResponseDTO> getSalesByStore(String storeId);
    List<SalesResponseDTO> getSalesByTitle(String titleId);
    List<SalesResponseDTO> getSalesByDateRange(LocalDateTime from, LocalDateTime to);
}