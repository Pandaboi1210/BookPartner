package com.sprint.BookPartnerApplication.dto.response.report;

public class TopAuthorDTO {

    private String auId;
    private String authorName;
    private Integer totalBooksSold;

    public TopAuthorDTO(String auId, String authorName, Integer totalBooksSold) {
        this.auId = auId;
        this.authorName = authorName;
        this.totalBooksSold = totalBooksSold;
    }

    public String getAuId() { return auId; }
    public String getAuthorName() { return authorName; }
    public Integer getTotalBooksSold() { return totalBooksSold; }
}
