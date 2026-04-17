package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.dto.request.TitleRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.TitleResponseDTO;
import com.sprint.BookPartnerApplication.entity.Authors;
import com.sprint.BookPartnerApplication.entity.Roysched;
import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.services.TitleService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/titles")
public class TitleController 
{
    @Autowired
    private TitleService titleService;

    @GetMapping
    public List<TitleResponseDTO> getTitles(@RequestParam(required = false) String type, 
                                            @RequestParam(required = false) String publisher,
                                            @RequestParam(required = false) Double minPrice, 
                                            @RequestParam(required = false) Double maxPrice) 
    {
        if (type != null) {
            return titleService.getTitlesByType(type);
        } else if (publisher != null) {
            return titleService.getTitlesByPublisher(publisher);
        } else if (minPrice != null && maxPrice != null) {
            return titleService.getTitlesByPriceRange(minPrice, maxPrice);
        }
        
        return titleService.getAllTitles();
    }

    @GetMapping("/{id}")
    public TitleResponseDTO getTitleById(@PathVariable String id) 
    {
        return titleService.getTitleById(id);
    }

    @PostMapping
    public TitleResponseDTO createTitle(@Valid @RequestBody TitleRequestDTO titleDTO) 
    {
        return titleService.insertTitle(titleDTO); 
    }

    @PutMapping("/{id}")
    public TitleResponseDTO updateTitle(@PathVariable String id, @Valid @RequestBody TitleRequestDTO titleDTO) 
    { 
        return titleService.updateTitleById(id, titleDTO); 
    }

    @GetMapping("/{titleId}/authors")
    public List<Authors> getAuthorsByTitle(@PathVariable String titleId) 
    {
        return titleService.getAuthorsByTitle(titleId);
    }

    @GetMapping("/{titleId}/sales")
    public List<Sales> getSalesByTitle(@PathVariable String titleId) 
    {
        return titleService.getSalesByTitleId(titleId);
    }

    @GetMapping("/{titleId}/royalties")
    public List<Roysched> getRoyaltiesByTitle(@PathVariable String titleId) 
    {
        return titleService.getRoyaltiesByTitleId(titleId);
    }
}