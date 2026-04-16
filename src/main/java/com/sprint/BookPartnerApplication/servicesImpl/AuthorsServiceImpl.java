package com.sprint.BookPartnerApplication.servicesImpl;

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
    public void deleteAuthor(String id) {
        authorRepository.deleteById(id);
    }
    

   
    @Override
    public Authors getAuthorById(String authorId) {
        return authorRepository.findById(authorId).orElse(null);
    }


    @Override
    public Authors createAuthor(Authors author) {
        return authorRepository.save(author);
    }

    
    @Override
    public Authors updateAuthor(String authorId, Authors author) {
        Authors existing = authorRepository.findById(authorId).orElse(null);

        if (existing != null) {
           
            existing.setPhone(author.getPhone());
            existing.setAddress(author.getAddress());
            existing.setCity(author.getCity());
            existing.setState(author.getState());
            existing.setZip(author.getZip());

            return authorRepository.save(existing);
        }
        return null;
    }

    
    @Override
    public List<Title> getTitlesByAuthor(String authorId) {
        return authorRepository.getTitlesByAuthorId(authorId);
    }
}