package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.Publishers;
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
        return publisherRepository.findById(id).orElse(null);
    }

    
    @Override
    public Publishers createPublisher(Publishers publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    public Publishers updatePublisher(String id, Publishers publisher) {
        Publishers existing = publisherRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setPubName(publisher.getPubName());
            return publisherRepository.save(existing);
        }
        return null;
    }

   
    @Override
    public void deletePublisher(String id) {
        publisherRepository.deleteById(id);
    }

    @Override
    public List<Title> getTitlesByPublisher(String publisherId) {
        return publisherRepository.getTitlesByPublisherId(publisherId);
    }

 
    @Override
    public List<Employee> getEmployeesByPublisher(String publisherId) {
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