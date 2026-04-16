package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Discounts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discounts, Integer> {

	List<Discounts> findByStore_StorId(String storId);
}
