package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.SalesId;
import com.sprint.BookPartnerApplication.dto.response.report.SalesByTitleDTO;
import com.sprint.BookPartnerApplication.dto.response.report.SalesByPublisherDTO;
import com.sprint.BookPartnerApplication.dto.response.report.SalesTrendDTO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface SalesRepository extends JpaRepository<Sales, SalesId> 
{
    // Get sales by store id
    @Query("SELECT s FROM Sales s WHERE s.storId = :storId")
    List<Sales> findByStorId(String storId);

    // Get sales by title id
    @Query("SELECT s FROM Sales s WHERE s.titleId = :titleId")
    List<Sales> findByTitleId(String titleId);

    // Get sales between dates
    @Query("SELECT s FROM Sales s WHERE s.ordDate BETWEEN :from AND :to")
    List<Sales> findByOrdDateBetween(LocalDateTime from, LocalDateTime to);
    
 // ── Reporting queries ────────────────────────────────────────────────────

    // Get total sales grouped by title
    @Query("SELECT new com.sprint.BookPartnerApplication.dto.response.report.SalesByTitleDTO(" +
           "t.titleId, t.title, CAST(SUM(s.qty) AS integer), SUM(s.qty * t.price)) " +
           "FROM Sales s JOIN Title t ON s.titleId = t.titleId " +
           "GROUP BY t.titleId, t.title " +
           "ORDER BY SUM(s.qty) DESC")
    List<SalesByTitleDTO> getSalesByTitle();

    // Get total sales grouped by publisher
    @Query("SELECT new com.sprint.BookPartnerApplication.dto.response.report.SalesByPublisherDTO(" +
           "p.pubId, p.pubName, CAST(SUM(s.qty) AS integer), SUM(s.qty * t.price)) " +
           "FROM Sales s " +
           "JOIN Title t ON s.titleId = t.titleId " +
           "JOIN Publishers p ON t.publisher.pubId = p.pubId " +
           "GROUP BY p.pubId, p.pubName " +
           "ORDER BY SUM(s.qty) DESC")
    List<SalesByPublisherDTO> getSalesByPublisher();

    // Get sales trend for date range
    @Query("SELECT new com.sprint.BookPartnerApplication.dto.response.report.SalesTrendDTO(" +
           "COUNT(s), CAST(SUM(s.qty) AS long)) " +
           "FROM Sales s WHERE s.ordDate BETWEEN :from AND :to")
    SalesTrendDTO getSalesTrend(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}