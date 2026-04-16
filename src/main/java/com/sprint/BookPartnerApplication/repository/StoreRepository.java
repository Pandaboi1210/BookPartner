package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
}
