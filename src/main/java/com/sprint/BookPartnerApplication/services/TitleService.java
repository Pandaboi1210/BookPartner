package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TitleService 
{

    @Autowired
    private TitleRepository titleRepository;

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
        return titleRepository.save(title);
    }

    public void deleteTitle(String titleId) 
    {
        titleRepository.deleteById(titleId);
    }
}