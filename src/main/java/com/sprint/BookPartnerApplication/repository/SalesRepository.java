package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.SalesId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface SalesRepository extends JpaRepository<Sales, SalesId> 
{
	@Query("SELECT s FROM Sales s WHERE s.storId = :storId")
    List<Sales> findByStorId(String storId);

    // ✅ 2. Find by Title ID (JPQL)
    @Query("SELECT s FROM Sales s WHERE s.titleId = :titleId")
    List<Sales> findByTitleId(String titleId);

    // ✅ 3. Date range (JPQL)
    @Query("SELECT s FROM Sales s WHERE s.ordDate BETWEEN :from AND :to")
    List<Sales> findByOrdDateBetween(LocalDateTime from, LocalDateTime to);
}