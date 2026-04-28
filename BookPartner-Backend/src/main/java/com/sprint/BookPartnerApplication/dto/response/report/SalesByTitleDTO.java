package com.sprint.BookPartnerApplication.dto.response.report;

/**
 * Data Transfer Object specifically designed for Reporting Projections.
 * This class flattens aggregated data from the 'Titles' and 'Sales' tables into a single, easy-to-consume object for the frontend dashboard.
 */

public class SalesByTitleDTO {

    private String titleId;
    private String titleName;
    private Integer totalQuantitySold;
    private Double totalRevenue;

    public SalesByTitleDTO(String titleId, String titleName, Integer totalQuantitySold, Double totalRevenue) {
        this.titleId = titleId;
        this.titleName = titleName;
        this.totalQuantitySold = totalQuantitySold;
        this.totalRevenue = totalRevenue;
    }

    public String getTitleId() { return titleId; }
    public String getTitleName() { return titleName; }
    public Integer getTotalQuantitySold() { return totalQuantitySold; }
    public Double getTotalRevenue() { return totalRevenue; }
}
