package com.sprint.BookPartnerApplication.controller;

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

    // 1. CREATE SALE
    // POST /api/v1/sales
    @PostMapping
    public ResponseEntity<Sales> createSale(@Valid @RequestBody Sales sale) {
        Sales created = salesService.createSale(sale);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // 2. GET ALL SALES
    // GET /api/v1/sales
    @GetMapping
    public ResponseEntity<List<Sales>> getAllSales() {
        return ResponseEntity.ok(salesService.getAllSales());
    }

    // 3. GET SALES BY STORE
    // GET /api/v1/sales/store/{storeId}
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<Sales>> getSalesByStore(@PathVariable String storeId) {
        return ResponseEntity.ok(salesService.getSalesByStore(storeId));
    }

    // 4. GET SALES BY TITLE
    // GET /api/v1/sales/title/{titleId}
    @GetMapping("/title/{titleId}")
    public ResponseEntity<List<Sales>> getSalesByTitle(@PathVariable String titleId) {
        return ResponseEntity.ok(salesService.getSalesByTitle(titleId));
    }

    // 5. GET SALES BY DATE RANGE
    // GET /api/v1/sales/date-range?from=&to=
    @GetMapping("/date-range")
    public ResponseEntity<List<Sales>> getSalesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        return ResponseEntity.ok(
                salesService.getSalesByDateRange(from, to)
        );
    }
}