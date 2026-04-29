package com.sprint.BookPartnerApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/*Represents the join table between Authors and Titles*/
@Entity
@Table(name = "titleauthor")
@IdClass(TitleAuthorId.class) // Specifies the composite primary key class
public class TitleAuthor 
{
    @Id
    @Column(name = "au_id")
    @NotBlank(message = "Author ID is required")
    private String auId;

    @Id
    @Column(name = "title_id")
    @NotBlank(message = "Title ID is required")
    private String titleId;

    @ManyToOne
    @JoinColumn(name = "au_id", insertable = false, updatable = false)
    private Authors author;

    @ManyToOne
    @JoinColumn(name = "title_id", insertable = false, updatable = false)
    private Title title; 

    @Column(name = "au_ord")
    private Byte auOrd; 

    private Integer royaltyper;
    
    
    // GETTERS & SETTERS
    public String getAuId() { return auId; }
    public void setAuId(String auId) { this.auId = auId; }

    public String getTitleId() { return titleId; }
    public void setTitleId(String titleId) { this.titleId = titleId; }

    public Authors getAuthor() { return author; }
    public void setAuthor(Authors author) { this.author = author; }

    public Title getTitle() { return title; }
    public void setTitle(Title title) { this.title = title; }

    public Byte getAuOrd() { return auOrd; }
    public void setAuOrd(Byte auOrd) { this.auOrd = auOrd; }

    public Integer getRoyaltyper() { return royaltyper; }
    public void setRoyaltyper(Integer royaltyper) { this.royaltyper = royaltyper; }
}