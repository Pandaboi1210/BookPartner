package com.sprint.BookPartnerApplication.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class SalesRequestDTO {

    @NotBlank(message = "Store ID is required")
    @Size(max = 4, message = "Store ID must be at most 4 characters")
    private String storId;

    @NotBlank(message = "Order number is required")
    @Size(max = 20, message = "Order number must be at most 20 characters")
    private String ordNum;

    @NotBlank(message = "Title ID is required")
    @Size(max = 10, message = "Title ID must be at most 10 characters")
    private String titleId;

    @NotNull(message = "Order date is required")
    private LocalDateTime ordDate;

    @Min(value = 1, message = "Quantity must be at least 1")
    private short qty;

    @NotBlank(message = "Payment terms are required")
    @Size(max = 12, message = "Payment terms must be at most 12 characters")
    private String payterms;

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