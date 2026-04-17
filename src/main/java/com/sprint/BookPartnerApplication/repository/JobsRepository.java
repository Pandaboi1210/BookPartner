package com.sprint.BookPartnerApplication.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sprint.BookPartnerApplication.entity.Jobs;
@Repository
public interface JobsRepository extends JpaRepository<Jobs, Short> {

	List<Jobs> findByMinLvlLessThanEqualAndMaxLvlGreaterThanEqual(int level, int level2);
	boolean existsByJobDesc(String jobDesc);
	

}