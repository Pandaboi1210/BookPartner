package com.sprint.BookPartnerApplication.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
@Embeddable
public class SaleId implements Serializable {
	private String stor_id;
    private String ord_num;
    private String title_id;


}
