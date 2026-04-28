package com.sprint.BookPartnerApplication.entity;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

/* Composite Primary Key class for the TitleAuthor entity.*/
public class TitleAuthorId implements Serializable 
{
    @NotBlank
    private String auId;
    
    @NotBlank
    private String titleId;

    // CONSTRUCTORS
    public TitleAuthorId() 
    {

    }

    public TitleAuthorId(String auId, String titleId) 
    {
        this.auId = auId;
        this.titleId = titleId;
    }

    // GETTERS & SETTERS

    public String getAuId() { return auId; }
    public void setAuId(String auId) { this.auId = auId; }

    public String getTitleId() { return titleId; }
    public void setTitleId(String titleId) { this.titleId = titleId; }

    // EQUALS & HASHCODE (Crucial for JPA)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TitleAuthorId that = (TitleAuthorId) o;
        
        // Checks if both the author ID and title ID match
        return Objects.equals(auId, that.auId) &&
               Objects.equals(titleId, that.titleId);
    }

    @Override
    public int hashCode() {
        // Generates a unique hash based on the combination of both IDs
        return Objects.hash(auId, titleId);
    }
}