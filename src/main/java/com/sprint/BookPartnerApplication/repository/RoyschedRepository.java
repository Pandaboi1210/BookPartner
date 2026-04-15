package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Roysched;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoyschedRepository extends JpaRepository<Roysched, Long> {
}
