package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Store;
import com.sprint.BookPartnerApplication.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> 
{
	
	
}