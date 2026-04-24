package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Title;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRepository extends JpaRepository<Title, String> {

    @Query("SELECT t FROM Title t WHERE t.type = :type")
    List<Title> findByType(@Param("type") String type);

    @Query("SELECT t FROM Title t WHERE t.publisher.pubId = :pubId")
    List<Title> findByPublisher_PubId(@Param("pubId") String pubId);

    @Query("SELECT t FROM Title t WHERE t.price >= :minPrice AND t.price <= :maxPrice")
    List<Title> findByPriceBetween(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);
}