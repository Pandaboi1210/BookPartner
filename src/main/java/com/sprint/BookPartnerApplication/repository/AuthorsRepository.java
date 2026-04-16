package com.sprint.BookPartnerApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sprint.BookPartnerApplication.entity.Authors;

public interface AuthorsRepository extends JpaRepository<Authors, String> 
{
	
} 