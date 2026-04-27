package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Roysched;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoyschedRepository extends JpaRepository<Roysched, Integer> {

    List<Roysched> findByTitle_TitleId(String titleId);


    @Query("SELECT r FROM Roysched r WHERE r.title.titleId = :titleId")
    List<Roysched> findByTitleId(@Param("titleId") String titleId);

    // @Query existence check
    @Query("""
           SELECT CASE WHEN COUNT(r) > 0
           THEN true ELSE false END
           FROM Roysched r
           WHERE r.title.titleId = :titleId
           AND r.lorange = :lorange
           AND r.hirange = :hirange
           """)
    boolean existsRoyschedRange(
            @Param("titleId") String titleId,
            @Param("lorange") Integer lorange,
            @Param("hirange") Integer hirange
    );

}