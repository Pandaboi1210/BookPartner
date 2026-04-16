package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.SalesId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;


@Repository
public interface SalesRepository extends JpaRepository<Sales, SalesId> {
    List<Sales> findByStorId(String storId);
    List<Sales> findByTitleId(String titleId);
    List<Sales> findByOrdDateBetween(LocalDateTime from, LocalDateTime to);
   
}