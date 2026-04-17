package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.*;
import com.sprint.BookPartnerApplication.exception.DuplicateResourceException;
import com.sprint.BookPartnerApplication.exception.InvalidInputException;
import com.sprint.BookPartnerApplication.exception.ResourceInUseException;
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
    { 
        return titleRepository.findAll(); 
    }

    // FIXED: just return the Optional — let the caller handle empty.
    // Throwing inside an Optional<> return type is contradictory.
    @Override
    public Optional<Title> getTitleById(String titleId) 
    { 
        return titleRepository.findById(titleId); 
    }

    @Override
    public Title insertTitle(Title title) 
    {
        if (title.getTitleId() != null && titleRepository.existsById(title.getTitleId())) {
            throw new DuplicateResourceException("Title already exists with ID: " + title.getTitleId());
        }

        if (title.getPublisher() != null && title.getPublisher().getPubId() != null) 
        {
            Publishers publisher = publishersRepository.findById(title.getPublisher().getPubId())
                    .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with ID: " + title.getPublisher().getPubId()));
            title.setPublisher(publisher);
        }
        return titleRepository.save(title);
    }

    @Override
    public Title updateTitleById(String titleId, Title updatedTitle) 
    {
        Title existingTitle = titleRepository.findById(titleId)
                .orElseThrow(() -> new ResourceNotFoundException("Title not found with ID: " + titleId));
        
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
            Publishers publisher = publishersRepository.findById(updatedTitle.getPublisher().getPubId())
                    .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with ID: " + updatedTitle.getPublisher().getPubId()));
            existingTitle.setPublisher(publisher);
        }
        return titleRepository.save(existingTitle);
    }

    @Override
    public void deleteTitle(Title title) 
    { 
        if (title != null && title.getTitleId() != null) {
            deleteTitleById(title.getTitleId());
        }
    }

    @Override
    public void deleteTitleById(String titleId) 
    { 
        if (!titleRepository.existsById(titleId)) {
            throw new ResourceNotFoundException("Title not found with ID: " + titleId);
        }

        if (!titleAuthorRepository.findByTitle_TitleId(titleId).isEmpty()) {
            throw new ResourceInUseException("Cannot delete Title. It has assigned authors.");
        }
        if (!salesRepository.findByTitleId(titleId).isEmpty()) {
            throw new ResourceInUseException("Cannot delete Title. It has existing sales records.");
        }
        if (!royschedRepository.findByTitle_TitleId(titleId).isEmpty()) {
            throw new ResourceInUseException("Cannot delete Title. It has existing royalty schedules.");
        }

        titleRepository.deleteById(titleId); 
    }

    @Override
    public List<Title> getTitlesByPublisher(String pubId) 
    { 
        if (!publishersRepository.existsById(pubId)) {
            throw new ResourceNotFoundException("Publisher not found with ID: " + pubId);
        }
        return titleRepository.findByPublisher_PubId(pubId); 
    }

    @Override
    public List<Title> getTitlesByType(String type) 
    { 
        return titleRepository.findByType(type); 
    }
    
    @Override
    public List<Authors> getAuthorsByTitle(String titleId) 
    {
        if (!titleRepository.existsById(titleId)) {
            throw new ResourceNotFoundException("Title not found with ID: " + titleId);
        }
        List<TitleAuthor> titleAuthors = titleAuthorRepository.findByTitle_TitleId(titleId);
        return titleAuthors.stream().map(TitleAuthor::getAuthor).toList(); 
    }

    @Override
    public List<Title> getTitlesByPriceRange(Double minPrice, Double maxPrice) 
    {
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new InvalidInputException("Minimum price cannot be greater than maximum price.");
        }
        return titleRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Sales> getSalesByTitleId(String titleId) 
    {
        if (!titleRepository.existsById(titleId)) {
            throw new ResourceNotFoundException("Title not found with ID: " + titleId);
        }
        return salesRepository.findByTitleId(titleId);
    }

    @Override
    public List<Roysched> getRoyaltiesByTitleId(String titleId) 
    {
        if (!titleRepository.existsById(titleId)) {
            throw new ResourceNotFoundException("Title not found with ID: " + titleId);
        }
        return royschedRepository.findByTitle_TitleId(titleId);
    }
}