package com.sprint.BookPartnerApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "stores")
public class Store {

    @Id
    @NotBlank(message = "Store ID is required")
    @Size(max = 4, message = "Store ID must be at most 4 characters")
    @Column(name = "stor_id", length = 4, nullable = false)
    private String storId;

    @Size(max = 40, message = "Store name must be at most 40 characters")
    @Column(name = "stor_name", length = 40, nullable = true)
    private String storName;

    @Size(max = 40, message = "Store address must be at most 40 characters")
    @Column(name = "stor_address", length = 40, nullable = true)
    private String storAddress;

    @Size(max = 20, message = "City must be at most 20 characters")
    @Column(name = "city", length = 20, nullable = true)
    private String city;

    @Size(max = 2, message = "State must be at most 2 characters")
    @Column(name = "state", length = 2, nullable = true)
    private String state;

    @Size(max = 5, message = "Zip must be at most 5 characters")
    @Pattern(regexp = "^[0-9]{5}$", message = "Zip must be exactly 5 digits")
    @Column(name = "zip", length = 5, nullable = true)
    private String zip;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sales> sales;

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

    public List<Sales> getSales() { return sales; }
    public void setSales(List<Sales> sales) { this.sales = sales; }
}