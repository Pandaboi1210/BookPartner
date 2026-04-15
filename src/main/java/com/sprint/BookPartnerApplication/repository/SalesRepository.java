package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.SalesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, SalesId> {

//    // Find all sales by store ID
//    List<Sales> findByStorId(String storId);
//
//    // Find all sales by title ID
//    List<Sales> findByTitleId(String titleId);
//
//    // Find all sales by order number
//    List<Sales> findByOrdNum(String ordNum);
//
//    // Find all sales by payment terms
//    List<Sales> findByPayterms(String payterms);
//
//    // Find sales between two dates
//    List<Sales> findByOrdDateBetween(LocalDateTime startDate, LocalDateTime endDate);
//
//    // Find sales by store ID and title ID
//    List<Sales> findByStorIdAndTitleId(String storId, String titleId);
}