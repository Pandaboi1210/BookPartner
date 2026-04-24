package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.SalesId;
import com.sprint.BookPartnerApplication.dto.response.report.SalesByTitleDTO;
import com.sprint.BookPartnerApplication.dto.response.report.SalesByPublisherDTO;
import com.sprint.BookPartnerApplication.dto.response.report.SalesTrendDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, SalesId> {

    @Query("SELECT s FROM Sales s JOIN FETCH s.store JOIN FETCH s.title")
    List<Sales> findAllWithDetails();

    @Query("SELECT s FROM Sales s JOIN FETCH s.store JOIN FETCH s.title WHERE s.titleId = :titleId")
    List<Sales> findByTitleId(@Param("titleId") String titleId);

    @Query("SELECT s FROM Sales s JOIN FETCH s.store JOIN FETCH s.title WHERE s.ordDate BETWEEN :from AND :to")
    List<Sales> findByOrdDateBetween(
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to
    );

    @Query("SELECT s FROM Sales s JOIN FETCH s.store JOIN FETCH s.title WHERE s.storId = :storeId")
    List<Sales> findByStorIdWithDetails(@Param("storeId") String storeId);

    @Query("SELECT new com.sprint.BookPartnerApplication.dto.response.report.SalesByTitleDTO(" +
           "t.titleId, t.title, CAST(SUM(s.qty) AS integer), SUM(s.qty * t.price)) " +
           "FROM Sales s JOIN s.title t " +
           "GROUP BY t.titleId, t.title " +
           "ORDER BY SUM(s.qty) DESC")
    List<SalesByTitleDTO> getSalesByTitle();

    @Query("SELECT new com.sprint.BookPartnerApplication.dto.response.report.SalesByPublisherDTO(" +
           "p.pubId, p.pubName, CAST(SUM(s.qty) AS integer), SUM(s.qty * t.price)) " +
           "FROM Sales s " +
           "JOIN s.title t " +
           "JOIN t.publisher p " +
           "GROUP BY p.pubId, p.pubName " +
           "ORDER BY SUM(s.qty) DESC")
    List<SalesByPublisherDTO> getSalesByPublisher();

    @Query("SELECT new com.sprint.BookPartnerApplication.dto.response.report.SalesTrendDTO(" +
           "COUNT(s), COALESCE(SUM(CAST(s.qty AS long)),0)) " +
           "FROM Sales s WHERE s.ordDate BETWEEN :from AND :to")
    SalesTrendDTO getSalesTrend(
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to
    );
}