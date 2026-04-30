package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.Publishers;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.entity.Employee;

import com.sprint.BookPartnerApplication.exception.DuplicateResourceException;
import com.sprint.BookPartnerApplication.exception.ResourceInUseException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;

import com.sprint.BookPartnerApplication.repository.PublishersRepository;
import com.sprint.BookPartnerApplication.services.PublisherService;

import com.sprint.BookPartnerApplication.dto.request.PublishersRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.PublishersResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private PublishersRepository publisherRepository;

    @Override
    public List<PublishersResponseDTO> getAllPublishers() {
        return publisherRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    
    @Override
    public PublishersResponseDTO getPublisherById(String id) {
        Publishers publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with ID: " + id));

        return mapToDTO(publisher);
    }

 
    @Override
    public PublishersResponseDTO createPublisher(PublishersRequestDTO dto) {

        if (dto.getPubId() != null && publisherRepository.existsById(dto.getPubId())) {
            throw new DuplicateResourceException("Publisher already exists with ID: " + dto.getPubId());
        }

        Publishers publisher = new Publishers();
        publisher.setPubId(dto.getPubId());
        publisher.setPubName(dto.getPubName());
        publisher.setCity(dto.getCity());
        publisher.setState(dto.getState());
        publisher.setCountry(dto.getCountry());

        Publishers saved = publisherRepository.save(publisher);

        return mapToDTO(saved);
    }

    @Override
    public PublishersResponseDTO updatePublisher(String id, PublishersRequestDTO dto) {

        Publishers existing = publisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with ID: " + id));

        existing.setPubName(dto.getPubName());
        existing.setCity(dto.getCity());
        existing.setState(dto.getState());
        existing.setCountry(dto.getCountry());

        Publishers updated = publisherRepository.save(existing);

        return mapToDTO(updated);
    }

 
    @Override
    public void deletePublisher(String id) {

        Publishers publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with ID: " + id));

       
        if (hasEmployees(id)) {
            throw new ResourceInUseException("Cannot delete Publisher with ID: " + id + ". It has employees.");
        }

        // check titles
        List<Title> titles = publisherRepository.getTitlesByPublisherId(id);
        if (titles != null && !titles.isEmpty()) {
            throw new ResourceInUseException("Cannot delete Publisher with ID: " + id + ". It has titles.");
        }

        publisherRepository.delete(publisher);
    }

   
    @Override
    public List<Title> getTitlesByPublisher(String publisherId) {
        if (!publisherRepository.existsById(publisherId)) {
            throw new ResourceNotFoundException("Publisher not found with ID: " + publisherId);
        }
        List<Title> titles = publisherRepository.getTitlesByPublisherId(publisherId);
        if (titles == null || titles.isEmpty()) {
            throw new ResourceNotFoundException("No titles found for Publisher with ID: " + publisherId);
        }
        return titles;
    }

    
    @Override
    public List<Employee> getEmployeesByPublisher(String publisherId) {
        if (!publisherRepository.existsById(publisherId)) {
            throw new ResourceNotFoundException("Publisher not found with ID: " + publisherId);
        }
        List<Employee> employees = publisherRepository.getEmployeesByPublisherId(publisherId);
        if (employees == null || employees.isEmpty()) {
            throw new ResourceNotFoundException("No employees found for Publisher with ID: " + publisherId);
        }
        return employees;
    }


    public boolean hasEmployees(String publisherId) {
        List<Employee> employees = publisherRepository.getEmployeesByPublisherId(publisherId);
        return employees != null && !employees.isEmpty();
    }

    
    private PublishersResponseDTO mapToDTO(Publishers publisher) {

        PublishersResponseDTO dto = new PublishersResponseDTO();

        dto.setPubId(publisher.getPubId());
        dto.setPubName(publisher.getPubName());
        dto.setCity(publisher.getCity());
        dto.setState(publisher.getState());
        dto.setCountry(publisher.getCountry());

        return dto;
    }
}