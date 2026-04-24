package com.sprint.BookPartnerApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
@IdClass(SalesId.class)
public class Sales {

    @Id
    @NotBlank(message = "Store ID is required")
    @Column(name = "stor_id", length = 4, nullable = false)
    private String storId;

    @Id
    @NotBlank(message = "Order number is required")
    @Column(name = "ord_num", length = 20, nullable = false)
    private String ordNum;

    @Id
    @NotBlank(message = "Title ID is required")
    @Column(name = "title_id", length = 10, nullable = false)
    private String titleId;

    @NotNull(message = "Order date is required")
    @Column(name = "ord_date", nullable = false)
    private LocalDateTime ordDate;

    @Column(name = "qty", nullable = false)
    private short qty;

    @NotBlank(message = "Payment terms are required")
    @Column(name = "payterms", length = 12, nullable = false)
    private String payterms;

    // ✅ KEY FIX: insertable=false, updatable=false because stor_id is already
    // mapped as @Id @Column above — without this Hibernate throws duplicate column error
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stor_id", insertable = false, updatable = false)
    private Store store;

    // ✅ KEY FIX: same fix for title_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_id", insertable = false, updatable = false)
    private Title title;

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

    public Store getStore() { return store; }
    public void setStore(Store store) { this.store = store; }

    public Title getTitle() { return title; }
    public void setTitle(Title title) { this.title = title; }
}