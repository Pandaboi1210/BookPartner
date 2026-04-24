package com.sprint.BookPartnerApplication.dto.response;

import java.time.LocalDateTime;

public class TitleResponseDTO {

    private String titleId;
    private String title;
    private String type;
    private Double price;
    private Double advance;
    private Integer royalty;
    private Integer ytdSales;
    private String notes;
    private LocalDateTime pubdate;
   
    private String pubId;
    private String publisherName;

    // --- Getters & Setters ---

    public String getTitleId() { return titleId; }
    public void setTitleId(String titleId) { this.titleId = titleId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Double getAdvance() { return advance; }
    public void setAdvance(Double advance) { this.advance = advance; }

    public Integer getRoyalty() { return royalty; }
    public void setRoyalty(Integer royalty) { this.royalty = royalty; }

    public Integer getYtdSales() { return ytdSales; }
    public void setYtdSales(Integer ytdSales) { this.ytdSales = ytdSales; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getPubdate() { return pubdate; }
    public void setPubdate(LocalDateTime pubdate) { this.pubdate = pubdate; }

    public String getPubId() { return pubId; }
    public void setPubId(String pubId) { this.pubId = pubId; }

    public String getPublisherName() { return publisherName; }
    public void setPublisherName(String publisherName) { this.publisherName = publisherName; }
}