package com.sprint.BookPartnerApplication.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sprint.BookPartnerApplication.entity.Jobs;
@Repository
public interface JobsRepository extends JpaRepository<Jobs, Long> {

}