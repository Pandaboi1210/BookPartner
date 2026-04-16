package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.*;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
import com.sprint.BookPartnerApplication.repository.*;
import com.sprint.BookPartnerApplication.services.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TitleServiceImpl implements TitleService 
{
    @Autowired
    private TitleRepository titleRepository;
    
    @Autowired
    private PublishersRepository publishersRepository;

    @Autowired
    private TitleAuthorRepository titleAuthorRepository;

    @Autowired
    private SalesRepository salesRepository; 

    @Autowired
    private RoyschedRepository royschedRepository; 
    
    @Override
    public List<Title> getAllTitles() 
    { return titleRepository.findAll(); }

    @Override
    public Optional<Title> getTitleById(String titleId) 
    { return titleRepository.findById(titleId); }

    @Override
    public Title insertTitle(Title title) 
    {
        if (title.getPublisher() != null && title.getPublisher().getPubId() != null) 
        {
            Publishers publisher = publishersRepository.findById(title.getPublisher().getPubId()).orElse(null);
            title.setPublisher(publisher);
        }
        return titleRepository.save(title);
    }

    @Override
    public Title updateTitleById(String titleId, Title updatedTitle) 
    {
        Optional<Title> existingTitleOpt = titleRepository.findById(titleId);
        if (existingTitleOpt.isPresent()) 
        {
            Title existingTitle = existingTitleOpt.get();
            existingTitle.setTitle(updatedTitle.getTitle());
            existingTitle.setType(updatedTitle.getType());
            existingTitle.setPrice(updatedTitle.getPrice());
            existingTitle.setAdvance(updatedTitle.getAdvance());
            existingTitle.setRoyalty(updatedTitle.getRoyalty());
            existingTitle.setYtdSales(updatedTitle.getYtdSales());
            existingTitle.setNotes(updatedTitle.getNotes());
            existingTitle.setPubdate(updatedTitle.getPubdate());
            
            if (updatedTitle.getPublisher() != null && updatedTitle.getPublisher().getPubId() != null) 
            {
                Publishers publisher = publishersRepository.findById(updatedTitle.getPublisher().getPubId()).orElse(null);
                existingTitle.setPublisher(publisher);
            }
            return titleRepository.save(existingTitle);
        } 
        else 
        {
            throw new ResourceNotFoundException("Title not found with ID: " + titleId);
        }
    }

    @Override
    public void deleteTitle(Title title) 
    { titleRepository.delete(title); }

    @Override
    public void deleteTitleById(String titleId) 
    { titleRepository.deleteById(titleId); }

    @Override
    public List<Title> getTitlesByPublisher(String pubId) 
    { return titleRepository.findByPublisher_PubId(pubId); }

    @Override
    public List<Title> getTitlesByType(String type) 
    { return titleRepository.findByType(type); }
    
    @Override
    public List<Authors> getAuthorsByTitle(String titleId) 
    {
        List<TitleAuthor> titleAuthors = titleAuthorRepository.findByTitle_TitleId(titleId);
        return titleAuthors.stream().map(TitleAuthor::getAuthor).toList(); 
    }

    @Override
    public List<Title> getTitlesByPriceRange(Double minPrice, Double maxPrice) 
    {
        return titleRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Sales> getSalesByTitleId(String titleId) 
    {
        return salesRepository.findByTitleId(titleId);
    }

    @Override
    public List<Roysched> getRoyaltiesByTitleId(String titleId) 
    {
        return royschedRepository.findByTitle_TitleId(titleId);
    }
}