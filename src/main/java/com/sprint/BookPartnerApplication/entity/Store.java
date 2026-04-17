package com.sprint.BookPartnerApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "stores")
public class Store {

    @Id
    @NotBlank(message = "Store ID is required")
    @JsonProperty("storId")
    @Column(name = "stor_id", length = 4, nullable = false)
    private String storId;

    @Column(name = "stor_name", length = 40)
    private String storName;

    @Column(name = "stor_address", length = 40)
    private String storAddress;

    @Column(name = "city", length = 20)
    private String city;

    @Column(name = "state", length = 2)
    private String state;

    @Column(name = "zip", length = 5)
    private String zip;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
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