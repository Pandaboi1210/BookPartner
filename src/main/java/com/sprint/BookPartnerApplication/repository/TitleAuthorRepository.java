package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.TitleAuthor;
import com.sprint.BookPartnerApplication.entity.TitleAuthorId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
import org.springframework.data.repository.query.Param; 
import org.springframework.stereotype.Repository;

@Repository
public interface TitleAuthorRepository extends JpaRepository<TitleAuthor, TitleAuthorId> {

    @Query("SELECT ta FROM TitleAuthor ta WHERE ta.titleId = :titleId")
    List<TitleAuthor> findByTitle_TitleId(@Param("titleId") String titleId);
    
}