package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.dto.response.report.AuthorRoyaltyDTO;
import com.sprint.BookPartnerApplication.dto.response.report.TopAuthorDTO;
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

    // ── Reporting queries ────────────────────────────────────────────────────

    @Query("SELECT new com.sprint.BookPartnerApplication.dto.response.report.AuthorRoyaltyDTO(" +
           "a.auId, CONCAT(a.auFname, ' ', a.auLname), t.titleId, t.title, " +
           "CAST(SUM(s.qty) AS integer), ta.royaltyper, " +
           "SUM(s.qty * t.price * ta.royaltyper / 100.0)) " +
           "FROM TitleAuthor ta " +
           "JOIN Authors a ON ta.auId = a.auId " +
           "JOIN Title t ON ta.titleId = t.titleId " +
           "JOIN Sales s ON s.titleId = t.titleId " +
           "GROUP BY a.auId, a.auFname, a.auLname, t.titleId, t.title, ta.royaltyper " +
           "ORDER BY a.auId, t.titleId")
    List<AuthorRoyaltyDTO> getAuthorRoyalties();

    @Query("SELECT new com.sprint.BookPartnerApplication.dto.response.report.TopAuthorDTO(" +
           "a.auId, CONCAT(a.auFname, ' ', a.auLname), CAST(SUM(s.qty) AS integer)) " +
           "FROM TitleAuthor ta " +
           "JOIN Authors a ON ta.auId = a.auId " +
           "JOIN Sales s ON s.titleId = ta.titleId " +
           "GROUP BY a.auId, a.auFname, a.auLname " +
           "ORDER BY SUM(s.qty) DESC")
    List<TopAuthorDTO> getTopAuthorsBySales();
}