package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.Authors;
import com.sprint.BookPartnerApplication.entity.Title;

import java.util.List;

public interface AuthorsService {


    List<Authors> getAllAuthors();

    Authors getAuthorById(String authorId);

 
    Authors createAuthor(Authors author);

    
    Authors updateAuthor(String authorId, Authors author);

    
    List<Title> getTitlesByAuthor(String authorId);
    
    void deleteAuthor(String id);
}