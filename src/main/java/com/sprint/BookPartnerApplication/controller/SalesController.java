package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.SalesRequestDTO;
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

    // POST /api/v1/sales
    @PostMapping
    public ResponseEntity<Sales> createSale(@Valid @RequestBody SalesRequestDTO dto) {
        Sales created = salesService.createSale(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/v1/sales
    @GetMapping
    public ResponseEntity<List<Sales>> getAllSales() {
        List<Sales> sales = salesService.getAllSales();
        return ResponseEntity.ok(sales);
    }

    // GET /api/v1/sales/store/{storeId}
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<Sales>> getSalesByStore(@PathVariable String storeId) {
        List<Sales> sales = salesService.getSalesByStore(storeId);
        return ResponseEntity.ok(sales);
    }

    // GET /api/v1/sales/title/{titleId}
    @GetMapping("/title/{titleId}")
    public ResponseEntity<List<Sales>> getSalesByTitle(@PathVariable String titleId) {
        List<Sales> sales = salesService.getSalesByTitle(titleId);
        return ResponseEntity.ok(sales);
    }

    // GET /api/v1/sales/date-range?from=&to=
    @GetMapping("/date-range")
    public ResponseEntity<List<Sales>> getSalesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        List<Sales> sales = salesService.getSalesByDateRange(from, to);
        return ResponseEntity.ok(sales);
    }
}