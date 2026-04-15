package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.Authors;
import com.sprint.BookPartnerApplication.repository.AuthorsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorsService {

    @Autowired
    private AuthorsRepository authorRepository;

    public List<Authors> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Authors getAuthorById(String id) {
        Optional<Authors> author = authorRepository.findById(id);
        return author.orElse(null); 
    }

    public Authors saveAuthor(Authors author) {
        return authorRepository.save(author);
    }

    public void deleteAuthor(String id) {
        authorRepository.deleteById(id);
    }
}