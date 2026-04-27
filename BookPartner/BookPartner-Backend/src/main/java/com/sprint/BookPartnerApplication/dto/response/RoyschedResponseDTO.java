package com.sprint.BookPartnerApplication.dto.response;

public class RoyschedResponseDTO {

    private Integer royschedId;
    private Integer lorange;
    private Integer hirange;
    private Integer royalty;
    private String titleId;
    private String titleName;

    public RoyschedResponseDTO() {
    }

    public Integer getRoyschedId() {
        return royschedId;
    }

    public void setRoyschedId(Integer royschedId) {
        this.royschedId = royschedId;
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

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
}