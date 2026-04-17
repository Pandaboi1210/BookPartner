package com.sprint.BookPartnerApplication.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class DiscountRequestDTO {

    @Size(max = 40, message = "Discount type must not exceed 40 characters")
    private String discounttype;

    @PositiveOrZero(message = "Low quantity cannot be negative")
    private Integer lowqty;

    @PositiveOrZero(message = "High quantity cannot be negative")
    private Integer highqty;

    @DecimalMin(value = "0.00", message = "Discount must be greater than or equal to 0")
    @DecimalMax(value = "99.99", message = "Discount cannot exceed 99.99")
    @Digits(integer = 2, fraction = 2, message = "Discount must have up to 2 digits and 2 decimals")
    private BigDecimal discount;

    @Size(min = 4, max = 4, message = "Store ID must be exactly 4 characters")
    private String storId;

    public DiscountRequestDTO() {}

    public String getDiscounttype() {
        return discounttype;
    }

    public void setDiscounttype(String discounttype) {
        this.discounttype = discounttype;
    }

    public Integer getLowqty() {
        return lowqty;
    }

    public void setLowqty(Integer lowqty) {
        this.lowqty = lowqty;
    }

    public Integer getHighqty() {
        return highqty;
    }

    public void setHighqty(Integer highqty) {
        this.highqty = highqty;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getStorId() {
        return storId;
    }

    public void setStorId(String storId) {
        this.storId = storId;
    }
}