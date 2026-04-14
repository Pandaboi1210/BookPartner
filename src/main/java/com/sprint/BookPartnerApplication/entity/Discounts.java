package com.sprint.BookPartnerApplication.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "discounts")
public class Discounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer discountId;

    @Column(name = "discounttype")
    private String discounttype;

    @Column(name = "lowqty")
    private Integer lowqty;

    @Column(name = "highqty")
    private Integer highqty;

    @Column(name = "discount")
    private BigDecimal discount;

    @ManyToOne
    @JoinColumn(name = "stor_id")
    private Store store;

    public Discounts() {
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

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}