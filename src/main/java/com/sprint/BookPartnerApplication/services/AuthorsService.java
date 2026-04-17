package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.AuthorsRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.AuthorsResponseDTO;
import com.sprint.BookPartnerApplication.entity.Title;

import java.util.List;

public interface AuthorsService {

    List<AuthorsResponseDTO> getAllAuthors();

    AuthorsResponseDTO getAuthorById(String id);

    AuthorsResponseDTO createAuthor(AuthorsRequestDTO dto);

    AuthorsResponseDTO updateAuthor(String id, AuthorsRequestDTO dto);

    void deleteAuthor(String id);

    List<Title> getTitlesByAuthor(String authorId);
}