package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.SalesId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, SalesId> {

    // For GET /api/v1/sales/store/{storeId}
    List<Sales> findByStorId(String storId);

    // For GET /api/v1/sales/title/{titleId}
    List<Sales> findByTitleId(String titleId);

    // For GET /api/v1/sales/date-range?from=&to=
    List<Sales> findByOrdDateBetween(LocalDateTime from, LocalDateTime to);
   
}