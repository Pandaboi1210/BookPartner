package com.sprint.BookPartnerApplication.dto.response;



import java.time.LocalDateTime;

public class SalesResponseDTO {

    private String storId;
    private String ordNum;
    private String titleId;
    private LocalDateTime ordDate;
    private short qty;
    private String payterms;

    // Optional enriched fields from related entities
    private String storeName;   // from Store
    private String titleName;   // from Title

    // Getters & Setters
    public String getStorId() { return storId; }
    public void setStorId(String storId) { this.storId = storId; }

    public String getOrdNum() { return ordNum; }
    public void setOrdNum(String ordNum) { this.ordNum = ordNum; }

    public String getTitleId() { return titleId; }
    public void setTitleId(String titleId) { this.titleId = titleId; }

    public LocalDateTime getOrdDate() { return ordDate; }
    public void setOrdDate(LocalDateTime ordDate) { this.ordDate = ordDate; }

    public short getQty() { return qty; }
    public void setQty(short qty) { this.qty = qty; }

    public String getPayterms() { return payterms; }
    public void setPayterms(String payterms) { this.payterms = payterms; }

    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }

    public String getTitleName() { return titleName; }
    public void setTitleName(String titleName) { this.titleName = titleName; }
}
