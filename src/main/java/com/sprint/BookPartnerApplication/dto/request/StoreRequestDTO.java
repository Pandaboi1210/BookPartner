package com.sprint.BookPartnerApplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class StoreRequestDTO {

    @NotBlank(message = "Store ID is required")
    @Size(max = 4, message = "Store ID must be at most 4 characters")
    private String storId;

    @Size(max = 40, message = "Store name must be at most 40 characters")
    private String storName;

    @Size(max = 40, message = "Store address must be at most 40 characters")
    private String storAddress;

    @Size(max = 20, message = "City must be at most 20 characters")
    private String city;

    @Size(max = 2, message = "State must be at most 2 characters")
    private String state;

    @Pattern(regexp = "^[0-9]{5}$", message = "Zip must be exactly 5 digits")
    @Size(max = 5, message = "Zip must be at most 5 characters")
    private String zip;

    public String getStorId() { return storId; }
    public void setStorId(String storId) { this.storId = storId; }

    public String getStorName() { return storName; }
    public void setStorName(String storName) { this.storName = storName; }

    public String getStorAddress() { return storAddress; }
    public void setStorAddress(String storAddress) { this.storAddress = storAddress; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }
}