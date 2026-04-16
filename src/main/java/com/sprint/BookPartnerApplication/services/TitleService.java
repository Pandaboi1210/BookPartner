package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.entity.Publishers;
import com.sprint.BookPartnerApplication.repository.TitleRepository;
import com.sprint.BookPartnerApplication.repository.PublishersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TitleService 
{

    @Autowired
    private TitleRepository titleRepository;
    
    @Autowired
    private PublishersRepository publishersRepository;

    public List<Title> getAllTitles() 
    {
        return titleRepository.findAll();
    }

    public Optional<Title> getTitleById(String titleId) 
    {
        return titleRepository.findById(titleId);
    }

    public Title saveTitle(Title title) 
    {
        // If a publisher is provided, fetch it from DB to avoid detached entity issues
        if (title.getPublisher() != null && title.getPublisher().getPubId() != null) {
            Publishers publisher = publishersRepository.findById(title.getPublisher().getPubId())
                    .orElse(null);
            title.setPublisher(publisher);
        }
        return titleRepository.save(title);
    }

    public void deleteTitle(String titleId) 
    {
        titleRepository.deleteById(titleId);
    }
}