package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Title;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRepository extends JpaRepository<Title, String> 
{
	List<Title> findByType(String type);
    List<Title> findByPublisher_PubId(String pubId);
    List<Title> findByPriceBetween(Double minPrice, Double maxPrice);
}
