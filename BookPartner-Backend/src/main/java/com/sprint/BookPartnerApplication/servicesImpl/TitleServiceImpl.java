package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.dto.request.TitleRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.TitleResponseDTO;
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
    

    // READ OPERATIONS
    @Override
    public List<TitleResponseDTO> getAllTitles() 
    { 
        return titleRepository.findAll().stream().map(this::mapToResponseDTO).toList(); 
    }

    @Override
    public List<TitleResponseDTO> getTitlesFiltered(String type, String publisher, Double minPrice, Double maxPrice) {
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new InvalidInputException("Minimum price cannot be greater than maximum price.");
        }

        String normalizedType = type == null ? null : type.trim();
        String normalizedPublisher = publisher == null ? null : publisher.trim();

        if (normalizedPublisher != null && !normalizedPublisher.isBlank() && !publishersRepository.existsById(normalizedPublisher)) {
            throw new ResourceNotFoundException("Publisher not found with ID: " + normalizedPublisher);
        }

        return titleRepository.findAll()
                .stream()
                .filter(t -> {
                    if (normalizedType == null || normalizedType.isBlank()) return true;
                    String tt = t.getType() == null ? "" : t.getType();
                    return tt.equalsIgnoreCase(normalizedType);
                })
                .filter(t -> {
                    if (normalizedPublisher == null || normalizedPublisher.isBlank()) return true;
                    return t.getPublisher() != null
                            && normalizedPublisher.equalsIgnoreCase(t.getPublisher().getPubId());
                })
                .filter(t -> {
                    if (minPrice == null) return true;
                    return t.getPrice() != null && t.getPrice() >= minPrice;
                })
                .filter(t -> {
                    if (maxPrice == null) return true;
                    return t.getPrice() != null && t.getPrice() <= maxPrice;
                })
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public TitleResponseDTO getTitleById(String titleId) 
    { 
        Title title = titleRepository.findById(titleId)
                .orElseThrow(() -> new ResourceNotFoundException("Title not found with ID: " + titleId));
        return mapToResponseDTO(title);
    }


    // WRITE OPERATIONS
    @Override
    public TitleResponseDTO insertTitle(TitleRequestDTO titleDTO) 
    {
        if (titleDTO.getTitleId() != null && titleRepository.existsById(titleDTO.getTitleId())) 
        {
            throw new DuplicateResourceException("Title already exists with ID: " + titleDTO.getTitleId());
        }
        
        Title title = new Title();
        title.setTitleId(titleDTO.getTitleId());
        title.setTitle(titleDTO.getTitle());
        title.setType(titleDTO.getType());
        title.setPrice(titleDTO.getPrice());
        title.setAdvance(titleDTO.getAdvance());
        title.setRoyalty(titleDTO.getRoyalty());
        title.setYtdSales(titleDTO.getYtdSales());
        title.setNotes(titleDTO.getNotes());
        title.setPubdate(titleDTO.getPubdate());

        if (titleDTO.getPubId() != null) 
        {
            Publishers publisher = publishersRepository.findById(titleDTO.getPubId())
                    .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with ID: " + titleDTO.getPubId()));
            title.setPublisher(publisher);
        }
        
        Title savedTitle = titleRepository.save(title);
        return mapToResponseDTO(savedTitle);
    }

    @Override
    public TitleResponseDTO updateTitleById(String titleId, TitleRequestDTO updatedTitleDTO) 
    {
        Title existingTitle = titleRepository.findById(titleId)
                .orElseThrow(() -> new ResourceNotFoundException("Title not found with ID: " + titleId));
        
        existingTitle.setTitle(updatedTitleDTO.getTitle());
        existingTitle.setType(updatedTitleDTO.getType());
        existingTitle.setPrice(updatedTitleDTO.getPrice());
        existingTitle.setAdvance(updatedTitleDTO.getAdvance());
        existingTitle.setRoyalty(updatedTitleDTO.getRoyalty());
        existingTitle.setYtdSales(updatedTitleDTO.getYtdSales());
        existingTitle.setNotes(updatedTitleDTO.getNotes());
        existingTitle.setPubdate(updatedTitleDTO.getPubdate());
        
        if (updatedTitleDTO.getPubId() != null) 
        {
            Publishers publisher = publishersRepository.findById(updatedTitleDTO.getPubId())
                    .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with ID: " + updatedTitleDTO.getPubId()));
            existingTitle.setPublisher(publisher);
        }
        
        Title savedTitle = titleRepository.save(existingTitle);
        return mapToResponseDTO(savedTitle);
    }

    // DELETE OPERATIONS
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


    // SUB-RESOURCE FETCHING
    @Override
    public List<TitleResponseDTO> getTitlesByPublisher(String pubId) 
    { 
        if (!publishersRepository.existsById(pubId)) {
            throw new ResourceNotFoundException("Publisher not found with ID: " + pubId);
        }
        return titleRepository.findByPublisher_PubId(pubId)
                .stream().map(this::mapToResponseDTO).toList(); 
    }

    @Override
    public List<TitleResponseDTO> getTitlesByType(String type) 
    { 
        return titleRepository.findByType(type)
                .stream().map(this::mapToResponseDTO).toList(); 
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
    public List<TitleResponseDTO> getTitlesByPriceRange(Double minPrice, Double maxPrice) 
    {
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new InvalidInputException("Minimum price cannot be greater than maximum price.");
        }
        return titleRepository.findByPriceBetween(minPrice, maxPrice)
                .stream().map(this::mapToResponseDTO).toList();
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


    // DATA MAPPING
    private TitleResponseDTO mapToResponseDTO(Title title) 
    {
        TitleResponseDTO dto = new TitleResponseDTO();
        dto.setTitleId(title.getTitleId());
        dto.setTitle(title.getTitle());
        dto.setType(title.getType());
        dto.setPrice(title.getPrice());
        dto.setAdvance(title.getAdvance());
        dto.setRoyalty(title.getRoyalty());
        dto.setYtdSales(title.getYtdSales());
        dto.setNotes(title.getNotes());
        dto.setPubdate(title.getPubdate());
        
        if (title.getPublisher() != null) {
            dto.setPubId(title.getPublisher().getPubId());
            dto.setPublisherName(title.getPublisher().getPubName());
        }
        
        return dto;
    }
}