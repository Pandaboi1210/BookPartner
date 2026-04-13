package com.sprint.BookPartnerApplication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "stores")
public class Stores {

    @Id
    @Column(name = "stor_id")
    private String id;

    @Column(name = "stor_name")
    private String name;

    @Column(name = "stor_address")
    private String address;

    private String city;
    private String state;
    private String zip;

//    @OneToMany(mappedBy = "store")
//    private List<Sale> sales;
//	

}
