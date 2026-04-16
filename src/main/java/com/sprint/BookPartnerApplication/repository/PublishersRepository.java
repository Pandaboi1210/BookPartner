package com.sprint.BookPartnerApplication.repository;

import com.sprint.BookPartnerApplication.entity.Publishers;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublishersRepository extends JpaRepository<Publishers, String> {

    @Query("SELECT t FROM Title t WHERE t.publisher.pubId = :publisherId")
    List<Title> getTitlesByPublisherId(@Param("publisherId") String publisherId);

    @Query("SELECT e FROM Employee e WHERE e.pubId = :publisherId")
    List<Employee> getEmployeesByPublisherId(@Param("publisherId") String publisherId);

    List<Publishers> findByPubName(String pubName);
}