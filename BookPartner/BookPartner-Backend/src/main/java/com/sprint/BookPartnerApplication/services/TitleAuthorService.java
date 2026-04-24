package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.TitleAuthorRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.TitleAuthorResponseDTO;

public interface TitleAuthorService 
{    
    TitleAuthorResponseDTO createTitleAuthor(TitleAuthorRequestDTO titleAuthorDTO);
    
    void deleteByAuthorAndTitle(String auId, String titleId);
}