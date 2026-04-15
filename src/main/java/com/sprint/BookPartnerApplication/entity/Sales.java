package com.sprint.BookPartnerApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
@IdClass(SalesId.class)
public class Sales {

    @Id
    @NotBlank(message = "Store ID is required")
    @Size(max = 4, message = "Store ID must be at most 4 characters")
    @Column(name = "stor_id", length = 4, nullable = false)
    private String storId;

    @Id
    @NotBlank(message = "Order number is required")
    @Size(max = 20, message = "Order number must be at most 20 characters")
    @Column(name = "ord_num", length = 20, nullable = false)
    private String ordNum;

    @Id
    @NotBlank(message = "Title ID is required")
    @Size(max = 10, message = "Title ID must be at most 10 characters")
    @Column(name = "title_id", length = 10, nullable = false)
    private String titleId;

    @NotNull(message = "Order date is required")
    @Column(name = "ord_date", nullable = false)
    private LocalDateTime ordDate;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(name = "qty", nullable = false)
    private short qty;

    @NotBlank(message = "Payment terms are required")
    @Size(max = 12, message = "Payment terms must be at most 12 characters")
    @Column(name = "payterms", length = 12, nullable = false)
    private String payterms;

    @ManyToOne
    @JoinColumn(name = "stor_id", insertable = false, updatable = false)
    private Store store;

    @ManyToOne
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