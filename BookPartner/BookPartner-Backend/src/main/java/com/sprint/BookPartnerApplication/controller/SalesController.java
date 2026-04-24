package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.SalesRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.SalesResponseDTO;
import com.sprint.BookPartnerApplication.services.SalesService;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
public class SalesController {

    private final SalesService salesService;

    
    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    
    @PostMapping
    public ResponseEntity<SalesResponseDTO> createSale(
            @Valid @RequestBody SalesRequestDTO dto) {

        SalesResponseDTO data = salesService.createSale(dto);

        return created(data);
    }

    
    @GetMapping
    public ResponseEntity<List<SalesResponseDTO>> getAllSales() {

        List<SalesResponseDTO> data = salesService.getAllSales();

        return ok(data);
    }

    
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<SalesResponseDTO>> getSalesByStore(
            @PathVariable String storeId) {

        List<SalesResponseDTO> data =
                salesService.getSalesByStore(storeId);

        return ok(data);
    }

   
    @GetMapping("/title/{titleId}")
    public ResponseEntity<List<SalesResponseDTO>> getSalesByTitle(
            @PathVariable String titleId) {

        List<SalesResponseDTO> data =
                salesService.getSalesByTitle(titleId);

        return ok(data);
    }

    
    @GetMapping("/date-range")
    public ResponseEntity<List<SalesResponseDTO>> getSalesByDateRange(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime from,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime to) {

        List<SalesResponseDTO> data =
                salesService.getSalesByDateRange(from, to);

        return ok(data);
    }

    
    private <T> ResponseEntity<T> ok(T data) {
        return ResponseEntity.ok(data);
    }

    private <T> ResponseEntity<T> created(T data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(data);
    }
}