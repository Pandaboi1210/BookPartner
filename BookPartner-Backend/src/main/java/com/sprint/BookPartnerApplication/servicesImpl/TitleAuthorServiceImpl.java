package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.dto.request.TitleAuthorRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.TitleAuthorResponseDTO;
import com.sprint.BookPartnerApplication.entity.TitleAuthor;
import com.sprint.BookPartnerApplication.entity.TitleAuthorId;
import com.sprint.BookPartnerApplication.exception.DuplicateResourceException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException; 
import com.sprint.BookPartnerApplication.repository.AuthorsRepository;
import com.sprint.BookPartnerApplication.repository.TitleAuthorRepository;
import com.sprint.BookPartnerApplication.repository.TitleRepository;
import com.sprint.BookPartnerApplication.services.TitleAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TitleAuthorServiceImpl implements TitleAuthorService 
{
    @Autowired
    private TitleAuthorRepository titleAuthorRepository;

    @Autowired
    private AuthorsRepository authorsRepository;

    @Autowired
    private TitleRepository titleRepository;


    // CREATE MAPPING    
    @Override
    public TitleAuthorResponseDTO createTitleAuthor(TitleAuthorRequestDTO titleAuthorDTO) 
    {
        // 1. Manually construct the composite key
        TitleAuthorId id = new TitleAuthorId();
        id.setAuId(titleAuthorDTO.getAuId()); 
        id.setTitleId(titleAuthorDTO.getTitleId());
        
        // 2. Fail-Fast Validation: Prevent composite key constraint violations in the DB
        if (titleAuthorRepository.existsById(id)) {
            throw new DuplicateResourceException(
                "Mapping already exists for Author: " + titleAuthorDTO.getAuId() + " and Title: " + titleAuthorDTO.getTitleId());
        }

        // 2b. Validate that Author and Title actually exist in the database
        if (!authorsRepository.existsById(titleAuthorDTO.getAuId())) {
            throw new ResourceNotFoundException("Author not found with ID: " + titleAuthorDTO.getAuId());
        }
        if (!titleRepository.existsById(titleAuthorDTO.getTitleId())) {
            throw new ResourceNotFoundException("Title not found with ID: " + titleAuthorDTO.getTitleId());
        }

        // 3. Map DTO to Entity
        TitleAuthor titleAuthor = new TitleAuthor();
        titleAuthor.setAuId(titleAuthorDTO.getAuId());
        titleAuthor.setTitleId(titleAuthorDTO.getTitleId());
        titleAuthor.setAuOrd(titleAuthorDTO.getAuOrd());
        titleAuthor.setRoyaltyper(titleAuthorDTO.getRoyaltyper());
        TitleAuthor savedTitleAuthor = titleAuthorRepository.save(titleAuthor);
        
        // 4. THE HYDRATION HACK: Re-fetch the saved entity from the DB.
        // Because Author and Title are marked insertable=false/updatable=false, 
        // the initial save() object doesn't contain the joined Author/Title data. 
        // Re-fetching ensures we have the full names to pass back to the frontend.
        TitleAuthor hydratedTitleAuthor = titleAuthorRepository.findById(id).orElse(savedTitleAuthor);
        return mapToResponseDTO(hydratedTitleAuthor);
    }


    // DELETE MAPPING
    @Override
    public void deleteByAuthorAndTitle(String auId, String titleId) 
    {    
        TitleAuthorId id = new TitleAuthorId();
        id.setAuId(auId);        
        id.setTitleId(titleId); 
        
        // Explicitly check existence so we can return a meaningful 404 error 
        // instead of a generic 500 Internal Server Error if the delete fails.
        if (!titleAuthorRepository.existsById(id)) 
        {
            throw new ResourceNotFoundException("Title-Author relationship not found for Author: " + auId + " and Title: " + titleId);
        }
        
        titleAuthorRepository.deleteById(id);
    }

    // DTO MAPPER (Data Enrichment)
    private TitleAuthorResponseDTO mapToResponseDTO(TitleAuthor titleAuthor) 
    {
        TitleAuthorResponseDTO dto = new TitleAuthorResponseDTO();
        
        dto.setAuId(titleAuthor.getAuId());
        dto.setTitleId(titleAuthor.getTitleId());
        dto.setAuOrd(titleAuthor.getAuOrd());
        dto.setRoyaltyper(titleAuthor.getRoyaltyper());

        // Null-safe checks to safely extract flattened string data 
        // from the associated objects.
        if (titleAuthor.getAuthor() != null) {
            dto.setAuthorFullName(titleAuthor.getAuthor().getAuFname() + " " + titleAuthor.getAuthor().getAuLname());
        }
        
        if (titleAuthor.getTitle() != null) {
            dto.setBookTitle(titleAuthor.getTitle().getTitle());
        }
        
        return dto;
    }
}