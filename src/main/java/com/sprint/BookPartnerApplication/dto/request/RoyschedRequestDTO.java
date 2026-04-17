package com.sprint.BookPartnerApplication.dto.request;

import jakarta.validation.constraints.NotNull;

public class RoyschedRequestDTO {

    @NotNull(message = "Title ID is required")
    private String titleId;

    private Integer lorange;

    private Integer hirange;

    private Integer royalty;

    public RoyschedRequestDTO() {
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public Integer getLorange() {
        return lorange;
    }

    public void setLorange(Integer lorange) {
        this.lorange = lorange;
    }

    public Integer getHirange() {
        return hirange;
    }

    public void setHirange(Integer hirange) {
        this.hirange = hirange;
    }

    public Integer getRoyalty() {
        return royalty;
    }

    public void setRoyalty(Integer royalty) {
        this.royalty = royalty;
    }
}