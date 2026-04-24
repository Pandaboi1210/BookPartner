package com.sprint.BookPartnerApplication.entity;

import java.io.Serializable;
import java.util.Objects;

public class SalesId implements Serializable {

    private String storId;
    private String ordNum;
    private String titleId;

    public SalesId() {}

    public SalesId(String storId, String ordNum, String titleId) {
        this.storId = storId;
        this.ordNum = ordNum;
        this.titleId = titleId;
    }

    public String getStorId() { return storId; }
    public void setStorId(String storId) { this.storId = storId; }

    public String getOrdNum() { return ordNum; }
    public void setOrdNum(String ordNum) { this.ordNum = ordNum; }

    public String getTitleId() { return titleId; }
    public void setTitleId(String titleId) { this.titleId = titleId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesId that = (SalesId) o;
        return Objects.equals(storId, that.storId) &&
               Objects.equals(ordNum, that.ordNum) &&
               Objects.equals(titleId, that.titleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storId, ordNum, titleId);
    }
}