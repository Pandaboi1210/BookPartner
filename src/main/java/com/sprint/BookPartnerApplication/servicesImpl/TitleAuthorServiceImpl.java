package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.TitleAuthor;
import com.sprint.BookPartnerApplication.entity.TitleAuthorId;
import com.sprint.BookPartnerApplication.exception.DuplicateResourceException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException; 
import com.sprint.BookPartnerApplication.repository.TitleAuthorRepository;
import com.sprint.BookPartnerApplication.services.TitleAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TitleAuthorServiceImpl implements TitleAuthorService 
{
    @Autowired
    private TitleAuthorRepository titleAuthorRepository;

    @Override
    public TitleAuthor createTitleAuthor(TitleAuthor titleAuthor) 
    {
        // Construct the composite ID to check if it already exists
        TitleAuthorId id = new TitleAuthorId();
        // Note: Make sure these getters match your actual TitleAuthor entity fields!
        id.setAuId(titleAuthor.getAuId()); 
        id.setTitleId(titleAuthor.getTitleId());
        
        // 🚨 409 CONFLICT: Prevent overwriting an existing author-title mapping
        if (titleAuthorRepository.existsById(id)) {
            throw new DuplicateResourceException(
                "Mapping already exists for Author: " + titleAuthor.getAuId() + " and Title: " + titleAuthor.getTitleId());
        }

        return titleAuthorRepository.save(titleAuthor);
    }
    
    @Override
    public void deleteByAuthorAndTitle(String auId, String titleId) 
    {    
        TitleAuthorId id = new TitleAuthorId();
        id.setAuId(auId);       
        id.setTitleId(titleId); 
        
        // 🚨 404 NOT FOUND: Your excellent existing check!
        if (!titleAuthorRepository.existsById(id)) 
        {
            throw new ResourceNotFoundException("Title-Author relationship not found for Author: " + auId + " and Title: " + titleId);
        }
        
        titleAuthorRepository.deleteById(id);
    }
}