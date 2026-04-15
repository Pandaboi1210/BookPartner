package com.sprint.BookPartnerApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.BookPartnerApplication.entity.Publishers;
import com.sprint.BookPartnerApplication.repository.PublishersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PublishersService {

    @Autowired
    private PublishersRepository repo;

    public Publishers savePublisher(Publishers publisher) {
        return repo.save(publisher);
    }

    // READ ALL
    public List<Publishers> getAllPublishers() {
        return repo.findAll();
    }

   
    public Optional<Publishers> getPublisherById(String id) {
        return repo.findById(id);
    }

    public Publishers updatePublisher(Publishers publisher) {
        return repo.save(publisher);
    }

    public void deletePublisher(String id) {
        repo.deleteById(id);
    }
}