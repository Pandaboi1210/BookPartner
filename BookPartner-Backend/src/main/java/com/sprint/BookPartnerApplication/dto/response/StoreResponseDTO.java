package com.sprint.BookPartnerApplication.dto.response;


import java.util.List;

public class StoreResponseDTO {

    private String storId;
    private String storName;
    private String storAddress;
    private String city;
    private String state;
    private String zip;

    // Optional enriched field — total sales count for this store
    private int totalSales;

    // Getters & Setters
    public String getStorId() { return storId; }
    public void setStorId(String storId) { this.storId = storId; }

    public String getStorName() { return storName; }
    public void setStorName(String storName) { this.storName = storName; }

    public String getStorAddress() { return storAddress; }
    public void setStorAddress(String storAddress) { this.storAddress = storAddress; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }

    public int getTotalSales() { return totalSales; }
    public void setTotalSales(int totalSales) { this.totalSales = totalSales; }
}

