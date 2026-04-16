package com.sprint.BookPartnerApplication.controller;

import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.servicesImpl.TitleServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/titles")
public class TitleController 
{

    @Autowired
    private TitleServiceImpl titleService;

    @GetMapping
    public List<Title> getAllTitles() 
    {
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
        return titleService.saveTitle(title);
    }

    @PutMapping("/{id}")
    public Title updateTitle(@PathVariable String id, @RequestBody Title title) 
    {
        title.setTitleId(id);
        return titleService.saveTitle(title);
    }

    @DeleteMapping("/{id}")
    public void deleteTitle(@PathVariable String id) 
    {
        titleService.deleteTitle(id);
    }
}