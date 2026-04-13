package com.sprint.BookPartnerApplication.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "sales")
public class Sales {
	 @EmbeddedId
	    private SaleId id;

	    private Integer qty;

	    private String payterms;

//	    // 🔗 Relationship with Store
//	    @ManyToOne
//	    @MapsId("stor_id")
//	    @JoinColumn(name = "stor_id")
//	    private Store store;
//
//	    // 🔗 Relationship with Book (titles)
//	    @ManyToOne
//	    @MapsId("title_id")
//	    @JoinColumn(name = "title_id")
//	    private Book book;
	

}
