package com.sprint.BookPartnerApplication.dto.request;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class SalesRequestDTO {

    @NotBlank(message = "Store ID is required")
    private String storId;

    @NotBlank(message = "Order number is required")
    private String ordNum;

    @NotBlank(message = "Title ID is required")
    private String titleId;

    @NotNull(message = "Order date is required")
    private LocalDateTime ordDate;

    private short qty;

    @NotBlank(message = "Payment terms are required")
    private String payterms;

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
}
