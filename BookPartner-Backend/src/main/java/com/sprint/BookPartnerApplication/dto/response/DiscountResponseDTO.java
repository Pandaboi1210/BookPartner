package com.sprint.BookPartnerApplication.dto.response;

import java.math.BigDecimal;

public class DiscountResponseDTO {

    private Integer discountId;
    private String discounttype;
    private Integer lowqty;
    private Integer highqty;
    private BigDecimal discount;
    private String storeId;
    private String storeName;

    public DiscountResponseDTO() {
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

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
        return storeId;
    }

    public void setStorId(String storId) {
        this.storeId = storId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}