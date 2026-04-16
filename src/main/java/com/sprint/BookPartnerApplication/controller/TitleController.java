package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.entity.Authors;
import com.sprint.BookPartnerApplication.entity.Roysched;
import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.services.TitleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/titles")
public class TitleController 
{
    @Autowired
    private TitleService titleService;

    @GetMapping
    public List<Title> getTitles(@RequestParam(required = false) String type, @RequestParam(required = false) String publisher,@RequestParam(required = false) Double minPrice, @RequestParam(required = false) Double maxPrice) 
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
    public Optional<Title> getTitleById(@PathVariable String id) 
    {
        return titleService.getTitleById(id);
    }

    @PostMapping
    public Title createTitle(@RequestBody Title title) 
    {
        return titleService.insertTitle(title); 
    }

    @PutMapping("/{id}")
    public Title updateTitle(@PathVariable String id, @RequestBody Title title) 
    { 
        return titleService.updateTitleById(id, title); 
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