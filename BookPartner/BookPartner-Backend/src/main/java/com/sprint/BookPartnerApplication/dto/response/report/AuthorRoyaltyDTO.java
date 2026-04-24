package com.sprint.BookPartnerApplication.dto.response.report;

public class AuthorRoyaltyDTO {

    private String auId;
    private String authorName;
    private String titleId;
    private String titleName;
    private Integer totalCopiesSold;
    private Integer royaltyPercent;     // author's share from titleauthor.royaltyper
    private Double estimatedRoyalty;    // totalCopiesSold * price * (royaltyper/100)

    public AuthorRoyaltyDTO(String auId, String authorName, String titleId, String titleName,
                            Integer totalCopiesSold, Integer royaltyPercent, Double estimatedRoyalty) {
        this.auId = auId;
        this.authorName = authorName;
        this.titleId = titleId;
        this.titleName = titleName;
        this.totalCopiesSold = totalCopiesSold;
        this.royaltyPercent = royaltyPercent;
        this.estimatedRoyalty = estimatedRoyalty;
    }

    public String getAuId() { return auId; }
    public String getAuthorName() { return authorName; }
    public String getTitleId() { return titleId; }
    public String getTitleName() { return titleName; }
    public Integer getTotalCopiesSold() { return totalCopiesSold; }
    public Integer getRoyaltyPercent() { return royaltyPercent; }
    public Double getEstimatedRoyalty() { return estimatedRoyalty; }
}
