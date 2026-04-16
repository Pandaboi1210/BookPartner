
    
package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Authors;
import com.sprint.BookPartnerApplication.entity.Title;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorsRepository extends JpaRepository<Authors, String> {

    List<Authors> findByName(String name);
    @Query("SELECT t FROM Titles t WHERE t.author.auId = :authorId")
    List<Title> getTitlesByAuthorId(@Param("authorId") String authorId);
}