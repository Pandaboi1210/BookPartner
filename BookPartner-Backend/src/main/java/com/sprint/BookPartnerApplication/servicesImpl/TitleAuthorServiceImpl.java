package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.dto.request.TitleAuthorRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.TitleAuthorResponseDTO;
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
    public TitleAuthorResponseDTO createTitleAuthor(TitleAuthorRequestDTO titleAuthorDTO) 
    {
        TitleAuthorId id = new TitleAuthorId();
        id.setAuId(titleAuthorDTO.getAuId()); 
        id.setTitleId(titleAuthorDTO.getTitleId());
        
        if (titleAuthorRepository.existsById(id)) {
            throw new DuplicateResourceException(
                "Mapping already exists for Author: " + titleAuthorDTO.getAuId() + " and Title: " + titleAuthorDTO.getTitleId());
        }
        TitleAuthor titleAuthor = new TitleAuthor();
        titleAuthor.setAuId(titleAuthorDTO.getAuId());
        titleAuthor.setTitleId(titleAuthorDTO.getTitleId());
        titleAuthor.setAuOrd(titleAuthorDTO.getAuOrd());
        titleAuthor.setRoyaltyper(titleAuthorDTO.getRoyaltyper());
        TitleAuthor savedTitleAuthor = titleAuthorRepository.save(titleAuthor);
        
        TitleAuthor hydratedTitleAuthor = titleAuthorRepository.findById(id).orElse(savedTitleAuthor);
        return mapToResponseDTO(hydratedTitleAuthor);
    }
    
    @Override
    public void deleteByAuthorAndTitle(String auId, String titleId) 
    {    
        TitleAuthorId id = new TitleAuthorId();
        id.setAuId(auId);        
        id.setTitleId(titleId); 
        
        if (!titleAuthorRepository.existsById(id)) 
        {
            throw new ResourceNotFoundException("Title-Author relationship not found for Author: " + auId + " and Title: " + titleId);
        }
        
        titleAuthorRepository.deleteById(id);
    }
    private TitleAuthorResponseDTO mapToResponseDTO(TitleAuthor titleAuthor) {
        TitleAuthorResponseDTO dto = new TitleAuthorResponseDTO();
        
        dto.setAuId(titleAuthor.getAuId());
        dto.setTitleId(titleAuthor.getTitleId());
        dto.setAuOrd(titleAuthor.getAuOrd());
        dto.setRoyaltyper(titleAuthor.getRoyaltyper());
        if (titleAuthor.getAuthor() != null) {
            dto.setAuthorFullName(titleAuthor.getAuthor().getAuFname() + " " + titleAuthor.getAuthor().getAuLname());
        }
        
        if (titleAuthor.getTitle() != null) {
            dto.setBookTitle(titleAuthor.getTitle().getTitle());
        }
        
        return dto;
    }
}