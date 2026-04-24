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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/sales")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @PostMapping
    public ResponseEntity<?> createSale(
            @Valid @RequestBody SalesRequestDTO dto) {
        try {
            SalesResponseDTO data = salesService.createSale(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"" + e.getMessage() + "\", \"cause\": \"" + e.getClass().getSimpleName() + "\"}");
        }
    }

    @GetMapping
    public ResponseEntity<List<SalesResponseDTO>> getAllSales() {
        List<SalesResponseDTO> data = salesService.getAllSales();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<SalesResponseDTO>> getSalesByStore(
            @PathVariable String storeId) {
        List<SalesResponseDTO> data = salesService.getSalesByStore(storeId);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/title/{titleId}")
    public ResponseEntity<List<SalesResponseDTO>> getSalesByTitle(
            @PathVariable String titleId) {
        List<SalesResponseDTO> data = salesService.getSalesByTitle(titleId);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<SalesResponseDTO>> getSalesByDateRange(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime from,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime to) {
        List<SalesResponseDTO> data = salesService.getSalesByDateRange(from, to);
        return ResponseEntity.ok(data);
    }
}