package com.sprint.BookPartnerApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "titles")
public class Title {

    @Id
    @Column(name = "title_id")
    @NotBlank(message = "Title ID is required")
    private String titleId;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Type is required")
    private String type = "UNDECIDED";

    @ManyToOne
    @JoinColumn(name = "pub_id")
    private Publishers publisher;

    private Double price;
    private Double advance;
    private Integer royalty;

    @Column(name = "ytd_sales")
    private Integer ytdSales;

    private String notes;

    @NotNull(message = "Publication date is required")
    private LocalDateTime pubdate;

    public String getTitleId() { return titleId; }
    public void setTitleId(String titleId) { this.titleId = titleId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Publishers getPublisher() { return publisher; }
    public void setPublisher(Publishers publisher) { this.publisher = publisher; }

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
}