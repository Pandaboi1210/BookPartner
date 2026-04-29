package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.TitleRequestDTO; 
import com.sprint.BookPartnerApplication.dto.response.TitleResponseDTO;
import com.sprint.BookPartnerApplication.entity.Authors;
import com.sprint.BookPartnerApplication.entity.Sales; 
import com.sprint.BookPartnerApplication.entity.Roysched;

import java.util.List;

public interface TitleService 
{
    List<TitleResponseDTO> getAllTitles();

    List<TitleResponseDTO> getTitlesFiltered(String type, String publisher, Double minPrice, Double maxPrice);

    TitleResponseDTO getTitleById(String titleId);

    TitleResponseDTO insertTitle(TitleRequestDTO titleDTO);

    TitleResponseDTO updateTitleById(String titleId, TitleRequestDTO updatedTitleDTO);
    
    void deleteTitleById(String titleId);

    List<Authors> getAuthorsByTitle(String titleId);

    List<Sales> getSalesByTitleId(String titleId);       

    List<Roysched> getRoyaltiesByTitleId(String titleId);

    List<TitleResponseDTO> getTitlesByPublisher(String pubId);
    List<TitleResponseDTO> getTitlesByType(String type);   
    List<TitleResponseDTO> getTitlesByPriceRange(Double minPrice, Double maxPrice);
}