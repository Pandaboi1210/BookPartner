package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.entity.TitleAuthor;
import com.sprint.BookPartnerApplication.entity.TitleAuthorId;
import com.sprint.BookPartnerApplication.services.TitleAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/title-authors")
public class TitleAuthorController 
{
    @Autowired
    private TitleAuthorService titleAuthorService;

    @GetMapping
    public List<TitleAuthor> getAllTitleAuthors()
    {
        return titleAuthorService.getAllTitleAuthors();
    }

    @GetMapping("/{auId}/{titleId}")
    public Optional<TitleAuthor> getTitleAuthorById(@PathVariable String auId, @PathVariable String titleId) 
    {
        TitleAuthorId id = new TitleAuthorId(auId, titleId);
        return titleAuthorService.getTitleAuthorById(id);
    }

    @PostMapping
    public TitleAuthor createTitleAuthor(@RequestBody TitleAuthor titleAuthor) 
    {
        return titleAuthorService.saveTitleAuthor(titleAuthor);
    }

    @PutMapping("/{auId}/{titleId}")
    public TitleAuthor updateTitleAuthor(@PathVariable String auId, @PathVariable String titleId, @RequestBody TitleAuthor titleAuthor) 
    {
        titleAuthor.setAuId(auId);
        titleAuthor.setTitleId(titleId);
        return titleAuthorService.saveTitleAuthor(titleAuthor);
    }

    @DeleteMapping("/{auId}/{titleId}")
    public void deleteTitleAuthor(@PathVariable String auId, @PathVariable String titleId) 
    {
        TitleAuthorId id = new TitleAuthorId(auId, titleId);
        titleAuthorService.deleteTitleAuthor(id);
    }
}