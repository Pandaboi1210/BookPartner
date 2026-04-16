package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.SalesId;
import com.sprint.BookPartnerApplication.servicesImpl.SalesServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesServiceImpl salesService;

    @GetMapping
    public List<Sales> getAllSales() {
        return salesService.getAllSales();
    }

    @GetMapping("/{storId}/{ordNum}/{titleId}")
    public Optional<Sales> getSalesById(
            @PathVariable String storId,
            @PathVariable String ordNum,
            @PathVariable String titleId) {

        SalesId id = new SalesId(storId, ordNum, titleId);
        return salesService.getSalesById(id);
    }

    @PostMapping
    public Sales createSales(@RequestBody Sales sales) {
        return salesService.saveSales(sales);
    }

    @PutMapping("/{storId}/{ordNum}/{titleId}")
    public Sales updateSales(
            @PathVariable String storId,
            @PathVariable String ordNum,
            @PathVariable String titleId,
            @RequestBody Sales sales) {

        sales.setStorId(storId);
        sales.setOrdNum(ordNum);
        sales.setTitleId(titleId);

        return salesService.saveSales(sales);
    }

    @DeleteMapping("/{storId}/{ordNum}/{titleId}")
    public void deleteSales(
            @PathVariable String storId,
            @PathVariable String ordNum,
            @PathVariable String titleId) {

        SalesId id = new SalesId(storId, ordNum, titleId);
        salesService.deleteSales(id);
    }
}