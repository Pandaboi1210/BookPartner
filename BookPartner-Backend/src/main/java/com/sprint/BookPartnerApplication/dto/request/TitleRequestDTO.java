package com.sprint.BookPartnerApplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for creating and updating Title records.
 * Acts as a protective barrier between the API layer and the database,
 * ensuring only valid, sanitized data reaches the Service layer.
 */

public class TitleRequestDTO 
{	
    @NotBlank(message = "Title ID is required")
    @Size(max = 10, message = "Title ID cannot exceed 10 characters")
    private String titleId;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 80, message = "Title cannot exceed 80 characters")
    private String title;

    @NotBlank(message = "Type is required")
    @Size(max = 12, message = "Type cannot exceed 12 characters")
    private String type = "UNDECIDED";

    @Size(max = 4, message = "Publisher ID cannot exceed 4 characters")
    private String pubId;

    @PositiveOrZero(message = "Price cannot be negative")
    private Double price;

    @PositiveOrZero(message = "Advance cannot be negative")
    private Double advance;

    @PositiveOrZero(message = "Royalty percentage cannot be negative")
    private Integer royalty;

    @PositiveOrZero(message = "Year-to-date sales cannot be negative")
    private Integer ytdSales;

    @Size(max = 200, message = "Notes cannot exceed 200 characters")
    private String notes;

    @NotNull(message = "Publication date is required")
    private LocalDateTime pubdate;

    
    
    public String getTitleId() { return titleId; }
    public void setTitleId(String titleId) { this.titleId = titleId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPubId() { return pubId; }
    public void setPubId(String pubId) { this.pubId = pubId; }

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