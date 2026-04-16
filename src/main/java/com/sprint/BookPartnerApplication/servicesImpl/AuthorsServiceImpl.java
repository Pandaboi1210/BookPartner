package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
import com.sprint.BookPartnerApplication.entity.Authors;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.repository.AuthorsRepository;
import com.sprint.BookPartnerApplication.services.AuthorsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorsServiceImpl implements AuthorsService {

    @Autowired
    private AuthorsRepository authorRepository;

    // ✅ Get all authors
    @Override
    public List<Authors> getAllAuthors() {
        return authorRepository.findAll();
    }

    // ✅ Get author by ID
    @Override
    public Authors getAuthorById(String id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    // ✅ Create author
    @Override
    public Authors createAuthor(Authors author) {
        return authorRepository.save(author);
    }

    // ✅ Update author (FIXED - using exception)
    @Override
    public Authors updateAuthor(String authorId, Authors author) {
        Authors existing = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + authorId));

        existing.setPhone(author.getPhone());
        existing.setAddress(author.getAddress());
        existing.setCity(author.getCity());
        existing.setState(author.getState());
        existing.setZip(author.getZip());

        return authorRepository.save(existing);
    }

    // ✅ Delete author
    @Override
    public void deleteAuthor(String id) {
        Authors author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        authorRepository.delete(author);
    }

    // ✅ Get titles by author
    @Override
    public List<Title> getTitlesByAuthor(String authorId) {
        return authorRepository.getTitlesByAuthorId(authorId);
    }
}