package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.Publishers;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.entity.Employee;

import java.util.List;

public interface PublisherService {


    List<Publishers> getAllPublishers();

    Publishers getPublisherById(String id);

    Publishers createPublisher(Publishers publisher);

    Publishers updatePublisher(String id, Publishers publisher);

    void deletePublisher(String id);


    List<Title> getTitlesByPublisher(String publisherId);

    List<Employee> getEmployeesByPublisher(String publisherId);

    boolean existsPublisher(String id);

    long countPublishers();

    List<Publishers> findPublishersByName(String name);

    boolean hasEmployees(String publisherId);
}