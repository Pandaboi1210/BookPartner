package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.response.report.AuthorRoyaltyDTO;
import com.sprint.BookPartnerApplication.dto.response.report.SalesByPublisherDTO;
import com.sprint.BookPartnerApplication.dto.response.report.SalesByTitleDTO;
import com.sprint.BookPartnerApplication.dto.response.report.SalesTrendDTO;
import com.sprint.BookPartnerApplication.dto.response.report.TopAuthorDTO;
import com.sprint.BookPartnerApplication.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // 1.1 Total Sales by Title
    // GET /api/v1/reports/sales/by-title
    @GetMapping("/sales/by-title")
    public List<SalesByTitleDTO> getSalesByTitle() {
        return reportService.getSalesByTitle();
    }

    // 1.2 Total Sales by Publisher
    // GET /api/v1/reports/sales/by-publisher
    @GetMapping("/sales/by-publisher")
    public List<SalesByPublisherDTO> getSalesByPublisher() {
        return reportService.getSalesByPublisher();
    }

    // 1.3 Sales Trend by Date Range
    // GET /api/v1/reports/sales/date-range?from=1993-01-01&to=1994-12-31
    @GetMapping("/sales/date-range")
    public SalesTrendDTO getSalesTrend(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {
        return reportService.getSalesTrend(from, to);
    }

    // 2.1 Author Earnings Report (Royalty Estimation)
    // GET /api/v1/reports/authors/royalties
    @GetMapping("/authors/royalties")
    public List<AuthorRoyaltyDTO> getAuthorRoyalties() {
        return reportService.getAuthorRoyalties();
    }

    // 2.2 Top Authors by Sales Volume
    // GET /api/v1/reports/authors/top-selling
    @GetMapping("/authors/top-selling")
    public List<TopAuthorDTO> getTopAuthorsBySales() {
        return reportService.getTopAuthorsBySales();
    }
}
