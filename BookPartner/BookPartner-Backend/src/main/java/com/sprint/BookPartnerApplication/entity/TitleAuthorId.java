package com.sprint.BookPartnerApplication.entity;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

public class TitleAuthorId implements Serializable {

    @NotBlank
    private String auId;
    
    @NotBlank
    private String titleId;

    public TitleAuthorId() {}

    public TitleAuthorId(String auId, String titleId) {
        this.auId = auId;
        this.titleId = titleId;
    }

    public String getAuId() { return auId; }
    public void setAuId(String auId) { this.auId = auId; }

    public String getTitleId() { return titleId; }
    public void setTitleId(String titleId) { this.titleId = titleId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TitleAuthorId that = (TitleAuthorId) o;
        return Objects.equals(auId, that.auId) &&
               Objects.equals(titleId, that.titleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auId, titleId);
    }
}