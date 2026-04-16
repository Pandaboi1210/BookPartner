
    
package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Authors;
import com.sprint.BookPartnerApplication.entity.Title;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorsRepository extends JpaRepository<Authors, String> {

<<<<<<< HEAD
    List<Authors> findByAuFname(String auFname);

    @Query("SELECT t FROM Title t WHERE t.author.auId = :authorId")
=======
    @Query("SELECT ta.title FROM TitleAuthor ta WHERE ta.auId = :authorId")
>>>>>>> refs/remotes/origin/master
    List<Title> getTitlesByAuthorId(@Param("authorId") String authorId);
}
