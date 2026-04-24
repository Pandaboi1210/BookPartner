package com.sprint.BookPartnerApplication.services;
import com.sprint.BookPartnerApplication.dto.request.PublishersRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.PublishersResponseDTO;
import com.sprint.BookPartnerApplication.entity.Employee;
import com.sprint.BookPartnerApplication.entity.Title;


import java.util.List;

public interface PublisherService {

    List<PublishersResponseDTO> getAllPublishers();

    PublishersResponseDTO getPublisherById(String id);

    PublishersResponseDTO createPublisher(PublishersRequestDTO dto);

    PublishersResponseDTO updatePublisher(String id, PublishersRequestDTO dto);

    void deletePublisher(String id);

    List<Title> getTitlesByPublisher(String publisherId);

    List<Employee> getEmployeesByPublisher(String publisherId);
}