package com.sprint.BookPartnerApplication.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TitleAuthorRequestDTO 
{
    @NotBlank(message = "Author ID is required")
    @Pattern(regexp = "^[0-9]{3}-[0-9]{2}-[0-9]{4}$", message = "Author ID must be in XXX-XX-XXXX format")
    private String auId;

    @NotBlank(message = "Title ID is required")
    @Size(max = 10, message = "Title ID cannot exceed 10 characters")
    private String titleId;

    @Min(value = 1, message = "Author order must be at least 1")
    private Byte auOrd;

    @Min(value = 0, message = "Royalty percentage cannot be negative")
    @Max(value = 100, message = "Royalty percentage cannot exceed 100")
    private Integer royaltyper;


    public String getAuId() { return auId; }
    public void setAuId(String auId) { this.auId = auId; }

    public String getTitleId() { return titleId; }
    public void setTitleId(String titleId) { this.titleId = titleId; }

    public Byte getAuOrd() { return auOrd; }
    public void setAuOrd(Byte auOrd) { this.auOrd = auOrd; }

    public Integer getRoyaltyper() { return royaltyper; }
    public void setRoyaltyper(Integer royaltyper) { this.royaltyper = royaltyper; }
}