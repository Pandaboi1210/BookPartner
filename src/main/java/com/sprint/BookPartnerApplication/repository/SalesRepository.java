package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.SalesId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Sales, SalesId> 
{
	List<Sales> findByTitle_TitleId(String titleId);
}