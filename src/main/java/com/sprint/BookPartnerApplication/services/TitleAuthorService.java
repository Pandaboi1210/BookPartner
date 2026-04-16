package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.TitleAuthor;

public interface TitleAuthorService 
{    
    TitleAuthor createTitleAuthor(TitleAuthor titleAuthor);
    
    void deleteByAuthorAndTitle(String auId, String titleId);
    
}