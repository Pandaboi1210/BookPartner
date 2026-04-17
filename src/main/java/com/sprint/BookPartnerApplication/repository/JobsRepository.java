package com.sprint.BookPartnerApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import com.sprint.BookPartnerApplication.entity.Jobs;

	List<Jobs> findByMinLvlLessThanEqualAndMaxLvlGreaterThanEqual(int level, int level2);
	boolean existsByJobDesc(String jobDesc);
	

    //CUSTOM UPDATE
    @Transactional
    @Modifying
    @Query("UPDATE Jobs j SET j.jobDesc = :desc, j.minLvl = :min, j.maxLvl = :max WHERE j.jobId = :id")
    void updateJobQuery(@Param("id") Short id,
                        @Param("desc") String desc,
                        @Param("min") int min,
                        @Param("max") int max);
}