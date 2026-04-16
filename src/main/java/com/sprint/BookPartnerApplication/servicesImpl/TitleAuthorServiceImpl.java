package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.TitleAuthor;
import com.sprint.BookPartnerApplication.entity.TitleAuthorId;
import com.sprint.BookPartnerApplication.repository.TitleAuthorRepository;
import com.sprint.BookPartnerApplication.services.TitleAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TitleAuthorServiceImpl implements TitleAuthorService {

    @Autowired
    private TitleAuthorRepository titleAuthorRepository;

    @Override
    public TitleAuthor createTitleAuthor(TitleAuthor titleAuthor) 
    {
        return titleAuthorRepository.save(titleAuthor);
    }
    
    @Override
    public void deleteByAuthorAndTitle(String auId, String titleId) 
    {    
        TitleAuthorId id = new TitleAuthorId();
        id.setAuId(auId);       
        id.setTitleId(titleId); 
        titleAuthorRepository.deleteById(id);
    }
}