package com.sprint.BookPartnerApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprint.BookPartnerApplication.entity.Authors;
import com.sprint.BookPartnerApplication.repository.AuthorsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorsService {

    @Autowired
    private AuthorsRepository repo;

    public Authors saveAuthor(Authors author) {
        return repo.save(author);
    }


    public List<Authors> getAllAuthors() {
        return repo.findAll();
    }

   
    public Optional<Authors> getAuthorById(String id) {
        return repo.findById(id);
    }


    public Authors updateAuthor(Authors author) {
        return repo.save(author);
    }

    public void deleteAuthor(String id) {
        repo.deleteById(id);
    }
}