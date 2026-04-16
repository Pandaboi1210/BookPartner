package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.Authors;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.entity.Sales; 
import com.sprint.BookPartnerApplication.entity.Roysched;
import java.util.List;
import java.util.Optional;

public interface TitleService 
{
    List<Title> getAllTitles();
    Optional<Title> getTitleById(String titleId);
    Title insertTitle(Title title);
    Title updateTitleById(String titleId, Title updatedTitle);
    void deleteTitle(Title title);
    void deleteTitleById(String titleId);

    List<Title> getTitlesByPublisher(String pubId);
    List<Title> getTitlesByType(String type);
    List<Authors> getAuthorsByTitle(String titleId);   

    List<Title> getTitlesByPriceRange(Double minPrice, Double maxPrice);
    List<Sales> getSalesByTitleId(String titleId);                       
    List<Roysched> getRoyaltiesByTitleId(String titleId);             
}