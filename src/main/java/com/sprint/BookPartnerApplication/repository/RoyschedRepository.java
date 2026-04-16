package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Roysched;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoyschedRepository extends JpaRepository<Roysched, Integer> {

    List<Roysched> findByTitle_TitleId(String titleId);

}