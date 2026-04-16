package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.TitleAuthor;
import com.sprint.BookPartnerApplication.entity.TitleAuthorId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleAuthorRepository extends JpaRepository<TitleAuthor, TitleAuthorId> 
{
	List<TitleAuthor> findByTitle_TitleId(String titleId);
}

