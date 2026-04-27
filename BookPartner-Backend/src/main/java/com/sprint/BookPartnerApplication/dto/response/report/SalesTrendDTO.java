package com.sprint.BookPartnerApplication.dto.response.report;

public class SalesTrendDTO {

    private Long totalSalesCount;
    private Long totalQuantitySold;

    public SalesTrendDTO(Long totalSalesCount, Long totalQuantitySold) {
        this.totalSalesCount = totalSalesCount;
        this.totalQuantitySold = totalQuantitySold;
    }

    public Long getTotalSalesCount() { return totalSalesCount; }
    public Long getTotalQuantitySold() { return totalQuantitySold; }
}
