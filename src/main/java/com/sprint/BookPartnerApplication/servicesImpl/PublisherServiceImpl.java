package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.Publishers;
import com.sprint.BookPartnerApplication.exception.DuplicateResourceException;
import com.sprint.BookPartnerApplication.exception.ResourceInUseException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;

import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.entity.Employee;

import com.sprint.BookPartnerApplication.repository.PublishersRepository;
import com.sprint.BookPartnerApplication.services.PublisherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private PublishersRepository publisherRepository;

    @Override
    public List<Publishers> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    public Publishers getPublisherById(String id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with id: " + id));
    }
    
    @Override
    public Publishers createPublisher(Publishers publisher) {
        // 🚨 409 CONFLICT: Prevent duplicating an existing publisher ID
        if (publisher.getPubId() != null && publisherRepository.existsById(publisher.getPubId())) {
            throw new DuplicateResourceException("Publisher already exists with ID: " + publisher.getPubId());
        }
        return publisherRepository.save(publisher);
    }

    @Override
    public Publishers updatePublisher(String id, Publishers publisher) {
        // 🚨 404 NOT FOUND: Replaced the 'return null' logic for much safer error handling
        Publishers existing = publisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with id: " + id));

        existing.setPubName(publisher.getPubName());
        return publisherRepository.save(existing);
    }

    @Override
    public void deletePublisher(String id) {
        Publishers publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with id: " + id));

        // 🚨 400 BAD REQUEST: Check if employees are still attached!
        if (hasEmployees(id)) {
            throw new ResourceInUseException("Cannot delete publisher. It currently has assigned employees.");
        }
        
        // 🚨 400 BAD REQUEST: Check if books are still attached!
        List<Title> titles = getTitlesByPublisher(id);
        if (titles != null && !titles.isEmpty()) {
            throw new ResourceInUseException("Cannot delete publisher. It currently has published titles.");
        }

        publisherRepository.delete(publisher);
    }
    
    @Override
    public List<Title> getTitlesByPublisher(String publisherId) {
        // Ensure publisher exists before checking for their titles
        if (!existsPublisher(publisherId)) {
            throw new ResourceNotFoundException("Publisher not found with id: " + publisherId);
        }
        return publisherRepository.getTitlesByPublisherId(publisherId);
    }

    @Override
    public List<Employee> getEmployeesByPublisher(String publisherId) {
        // Ensure publisher exists before checking for their employees
        if (!existsPublisher(publisherId)) {
            throw new ResourceNotFoundException("Publisher not found with id: " + publisherId);
        }
        return publisherRepository.getEmployeesByPublisherId(publisherId);
    }

    @Override
    public boolean existsPublisher(String id) {
        return publisherRepository.existsById(id);
    }

    @Override
    public long countPublishers() {
        return publisherRepository.count();
    }

    @Override
    public List<Publishers> findPublishersByName(String name) {
        return publisherRepository.findByPubName(name);
    }

    @Override
    public boolean hasEmployees(String publisherId) {
        List<Employee> employees = publisherRepository.getEmployeesByPublisherId(publisherId);
        return employees != null && !employees.isEmpty();
    }
}