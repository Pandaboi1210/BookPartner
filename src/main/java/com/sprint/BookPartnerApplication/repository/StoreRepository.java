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
	// ✅ 1. Get stores by city
    @Query("SELECT s FROM Store s WHERE s.city = :city")
    List<Store> findByCity(String city);

    // ✅ 2. Get stores by state
    @Query("SELECT s FROM Store s WHERE s.state = :state")
    List<Store> findByState(String state);
	
	
}