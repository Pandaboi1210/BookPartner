package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.exception.DuplicateResourceException;
import com.sprint.BookPartnerApplication.exception.ResourceInUseException;
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

    @Override
    public List<Authors> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Authors getAuthorById(String id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    @Override
    public Authors createAuthor(Authors author) 
    {
        if (author.getAuId() != null && authorRepository.existsById(author.getAuId())) {
            throw new DuplicateResourceException("Author already exists with ID: " + author.getAuId());
        }
        return authorRepository.save(author);
    }
    
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

    @Override
    public void deleteAuthor(String id) {
        Authors author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        List<Title> linkedTitles = authorRepository.getTitlesByAuthorId(id);
        if (linkedTitles != null && !linkedTitles.isEmpty()) {
            throw new ResourceInUseException("Cannot delete author. Author is currently assigned to one or more titles.");
        }

        authorRepository.delete(author);
    }

    @Override
    public List<Title> getTitlesByAuthor(String authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new ResourceNotFoundException("Author not found with id: " + authorId);
        }
        return authorRepository.getTitlesByAuthorId(authorId);
    }
}