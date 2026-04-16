package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.entity.TitleAuthor;
import com.sprint.BookPartnerApplication.services.TitleAuthorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/title-authors") 
public class TitleAuthorController {

    @Autowired
    private TitleAuthorService titleAuthorService;

    @PostMapping
    public TitleAuthor createTitleAuthor(@RequestBody TitleAuthor titleAuthor) {
        return titleAuthorService.createTitleAuthor(titleAuthor);
    }

    @DeleteMapping("/{auId}/{titleId}")
    public void deleteTitleAuthor(@PathVariable String auId, @PathVariable String titleId) 
    {    
        titleAuthorService.deleteByAuthorAndTitle(auId, titleId);
    }
}