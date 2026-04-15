package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.Publishers;
import com.sprint.BookPartnerApplication.repository.PublishersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublishersService {

    @Autowired
    private PublishersRepository publisherRepository;

    public List<Publishers> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Publishers getPublisherById(String id) {
        Optional<Publishers> publisher = publisherRepository.findById(id);
        return publisher.orElse(null);
    }

    public Publishers savePublisher(Publishers publisher) {
        return publisherRepository.save(publisher);
    }

    public void deletePublisher(String id) {
        publisherRepository.deleteById(id);
    }
}