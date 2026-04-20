package com.sprint.BookPartnerApplication.dto.request;

import jakarta.validation.constraints.*;

public class RoyschedRequestDTO {

    // Keeping your existing validation
    @NotNull(message = "Title ID is required")

    // title_id varchar(10)
    @Size(max = 10, message = "Title ID must not exceed 10 characters")
    private String titleId;

    // quantity ranges cannot be negative
    @PositiveOrZero(message = "Low range cannot be negative")
    private Integer lorange;

    @PositiveOrZero(message = "High range cannot be negative")
    private Integer hirange;

    // royalty percentage typically 0–100
    @Min(value = 0, message = "Royalty cannot be negative")
    @Max(value = 100, message = "Royalty cannot exceed 100")
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