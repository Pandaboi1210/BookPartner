package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {

//    // Find store by name
//    List<Store> findByStorName(String storName);
//
//    // Find stores by city
//    List<Store> findByCity(String city);
//
//    // Find stores by state
//    List<Store> findByState(String state);
//
//    // Find stores by city and state
//    List<Store> findByCityAndState(String city, String state);
//
//    // Find store by zip
//    List<Store> findByZip(String zip);
}
