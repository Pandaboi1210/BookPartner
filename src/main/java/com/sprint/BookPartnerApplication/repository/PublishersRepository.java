package com.sprint.BookPartnerApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sprint.BookPartnerApplication.entity.Publishers;

public interface PublishersRepository extends JpaRepository<Publishers, String> {
}