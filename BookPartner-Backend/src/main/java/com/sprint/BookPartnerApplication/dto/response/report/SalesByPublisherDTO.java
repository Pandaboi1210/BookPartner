package com.sprint.BookPartnerApplication.dto.response.report;

public class SalesByPublisherDTO {

    private String pubId;
    private String publisherName;
    private Integer totalQuantitySold;
    private Double totalRevenue;

    public SalesByPublisherDTO(String pubId, String publisherName, Integer totalQuantitySold, Double totalRevenue) {
        this.pubId = pubId;
        this.publisherName = publisherName;
        this.totalQuantitySold = totalQuantitySold;
        this.totalRevenue = totalRevenue;
    }

    public String getPubId() { return pubId; }
    public String getPublisherName() { return publisherName; }
    public Integer getTotalQuantitySold() { return totalQuantitySold; }
    public Double getTotalRevenue() { return totalRevenue; }
}
