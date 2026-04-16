package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Discounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discounts, Integer> {

    List<Discounts> findByDiscounttype(String discounttype);

    List<Discounts> findByStore_StorId(String storId);

}