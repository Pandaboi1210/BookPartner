package com.sprint.BookPartnerApplication.dto.response;

public class TitleAuthorResponseDTO {

    private String auId;
    private String titleId;
    
    private String authorFullName; 
    private String bookTitle;      

    private Byte auOrd;
    private Integer royaltyper;

    public String getAuId() { return auId; }
    public void setAuId(String auId) { this.auId = auId; }

    public String getTitleId() { return titleId; }
    public void setTitleId(String titleId) { this.titleId = titleId; }

    public String getAuthorFullName() { return authorFullName; }
    public void setAuthorFullName(String authorFullName) { this.authorFullName = authorFullName; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public Byte getAuOrd() { return auOrd; }
    public void setAuOrd(Byte auOrd) { this.auOrd = auOrd; }

    public Integer getRoyaltyper() { return royaltyper; }
    public void setRoyaltyper(Integer royaltyper) { this.royaltyper = royaltyper; }
}