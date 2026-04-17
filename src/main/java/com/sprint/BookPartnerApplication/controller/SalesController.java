package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.SalesRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.SalesResponseDTO;
import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.services.SalesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @PostMapping
    public ResponseEntity<SalesResponseDTO> createSale(@Valid @RequestBody SalesRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salesService.createSale(dto));
    }

    @GetMapping
    public ResponseEntity<List<SalesResponseDTO>> getAllSales() {
        return ResponseEntity.ok(salesService.getAllSales());
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<SalesResponseDTO>> getSalesByStore(@PathVariable String storeId) {
        return ResponseEntity.ok(salesService.getSalesByStore(storeId));
    }

    @GetMapping("/title/{titleId}")
    public ResponseEntity<List<SalesResponseDTO>> getSalesByTitle(@PathVariable String titleId) {
        return ResponseEntity.ok(salesService.getSalesByTitle(titleId));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<SalesResponseDTO>> getSalesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(salesService.getSalesByDateRange(from, to));
    }
}