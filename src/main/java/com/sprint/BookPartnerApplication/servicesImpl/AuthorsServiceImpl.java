package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.Authors;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.exception.DuplicateResourceException;
import com.sprint.BookPartnerApplication.exception.ResourceInUseException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
import com.sprint.BookPartnerApplication.repository.AuthorsRepository;
import com.sprint.BookPartnerApplication.services.AuthorsService;

import com.sprint.BookPartnerApplication.dto.request.AuthorsRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.AuthorsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorsServiceImpl implements AuthorsService {

    @Autowired
    private AuthorsRepository authorRepository;

  
    @Override
    public List<AuthorsResponseDTO> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

  
    @Override
    public AuthorsResponseDTO getAuthorById(String id) {
        Authors author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        return mapToDTO(author);
    }


    @Override
    public AuthorsResponseDTO createAuthor(AuthorsRequestDTO dto) {

        if (dto.getAuId() != null && authorRepository.existsById(dto.getAuId())) {
            throw new DuplicateResourceException("Author already exists with ID: " + dto.getAuId());
        }

        Authors author = new Authors();
        author.setAuId(dto.getAuId());
        author.setAuFname(dto.getAuFname());
        author.setAuLname(dto.getAuLname());
        author.setPhone(dto.getPhone());
        author.setAddress(dto.getAddress());
        author.setCity(dto.getCity());
        author.setState(dto.getState());
        author.setZip(dto.getZip());
        author.setContract(dto.getContract());

        Authors saved = authorRepository.save(author);

        return mapToDTO(saved);
    }

    @Override
    public AuthorsResponseDTO updateAuthor(String id, AuthorsRequestDTO dto) {

        Authors existing = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        existing.setAuFname(dto.getAuFname());
        existing.setAuLname(dto.getAuLname());
        existing.setPhone(dto.getPhone());
        existing.setAddress(dto.getAddress());
        existing.setCity(dto.getCity());
        existing.setState(dto.getState());
        existing.setZip(dto.getZip());
        existing.setContract(dto.getContract());

        Authors updated = authorRepository.save(existing);

        return mapToDTO(updated);
    }


    @Override
    public void deleteAuthor(String id) {
        Authors author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        List<Title> linkedTitles = authorRepository.getTitlesByAuthorId(id);

        if (linkedTitles != null && !linkedTitles.isEmpty()) {
            throw new ResourceInUseException("Cannot delete author. Author is assigned to one or more titles.");
        }

        authorRepository.delete(author);
    }

    
    @Override
    public List<Title> getTitlesByAuthor(String authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new ResourceNotFoundException("Author not found with id: " + authorId);
        }
        return authorRepository.getTitlesByAuthorId(authorId);
    }

    private AuthorsResponseDTO mapToDTO(Authors author) {

        AuthorsResponseDTO dto = new AuthorsResponseDTO();

        dto.setAuId(author.getAuId());
        dto.setAuFname(author.getAuFname());
        dto.setAuLname(author.getAuLname());
        dto.setPhone(author.getPhone());
        dto.setCity(author.getCity());
        dto.setState(author.getState());

        return dto;
    }
}