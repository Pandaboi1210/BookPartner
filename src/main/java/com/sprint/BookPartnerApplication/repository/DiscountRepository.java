package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Discounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiscountRepository extends JpaRepository<Discounts, Integer> {

    // Find by discount type
    @Query("SELECT d FROM Discounts d WHERE d.discounttype = :type")
    List<Discounts> findDiscountsByType(
            @Param("type") String discounttype
    );

    // Find by store ID
    @Query("SELECT d FROM Discounts d WHERE d.store.storId = :storId")
    List<Discounts> findDiscountsByStoreId(
            @Param("storId") String storId
    );

}