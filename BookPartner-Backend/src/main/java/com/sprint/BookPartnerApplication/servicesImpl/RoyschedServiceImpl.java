package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.dto.request.RoyschedRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.RoyschedResponseDTO;
import com.sprint.BookPartnerApplication.entity.Roysched;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.exception.BadRequestException;
import com.sprint.BookPartnerApplication.exception.DuplicateResourceException;
import com.sprint.BookPartnerApplication.exception.InvalidInputException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
import com.sprint.BookPartnerApplication.repository.RoyschedRepository;
import com.sprint.BookPartnerApplication.repository.TitleRepository;
import com.sprint.BookPartnerApplication.services.RoyschedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoyschedServiceImpl implements RoyschedService {

    @Autowired
    private RoyschedRepository royschedRepository;

    @Autowired
    private TitleRepository titleRepository;

    // ── mapping helpers ──────────────────────────────────────────────────────

    private RoyschedResponseDTO toResponse(Roysched r) {
        RoyschedResponseDTO dto = new RoyschedResponseDTO();
        dto.setRoyschedId(r.getRoyschedId());
        dto.setLorange(r.getLorange());
        dto.setHirange(r.getHirange());
        dto.setRoyalty(r.getRoyalty());
        if (r.getTitle() != null) {
            dto.setTitleId(r.getTitle().getTitleId());
            dto.setTitleName(r.getTitle().getTitle());
        }
        return dto;
    }

    // ── service methods ───────────────────────────────────────────────────────

    @Override
    public RoyschedResponseDTO createRoysched(RoyschedRequestDTO requestDTO) {

        if (requestDTO.getTitleId() == null || requestDTO.getTitleId().isBlank()) {
            throw new BadRequestException("A valid Title ID is required to create a royalty schedule.");
        }

        Title title = titleRepository.findById(requestDTO.getTitleId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Title not found with ID: " + requestDTO.getTitleId()));

        if (requestDTO.getLorange() != null && requestDTO.getHirange() != null) {
            if (requestDTO.getLorange() > requestDTO.getHirange()) {
                throw new InvalidInputException("Low range (lorange) cannot be greater than high range (hirange).");
            }
        }

        if (royschedRepository.existsRoyschedRange(
                title.getTitleId(),
                requestDTO.getLorange(),
                requestDTO.getHirange())) {

            throw new DuplicateResourceException(
                    "A royalty schedule with this exact low and high range already exists for this title.");
        }

        Roysched roysched = new Roysched();
        roysched.setTitle(title);
        roysched.setLorange(requestDTO.getLorange());
        roysched.setHirange(requestDTO.getHirange());
        roysched.setRoyalty(requestDTO.getRoyalty());

        return toResponse(royschedRepository.save(roysched));
    }

    @Override
    public List<RoyschedResponseDTO> getRoyschedByTitle(String titleId) {
        List<Roysched> schedules = royschedRepository.findByTitle_TitleId(titleId);

        if (schedules.isEmpty()) {
            throw new ResourceNotFoundException("No royalty schedules found for title ID: " + titleId);
        }

        return schedules.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RoyschedResponseDTO updateRoysched(Integer royaltyId, RoyschedRequestDTO requestDTO) {

        Roysched roysched = royschedRepository.findById(royaltyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Royalty slab not found with id: " + royaltyId));

        if (requestDTO.getLorange() != null && requestDTO.getHirange() != null) {
            if (requestDTO.getLorange() > requestDTO.getHirange()) {
                throw new InvalidInputException("Low range (lorange) cannot be greater than high range (hirange).");
            }
        }

        roysched.setLorange(requestDTO.getLorange());
        roysched.setHirange(requestDTO.getHirange());
        roysched.setRoyalty(requestDTO.getRoyalty());

        if (requestDTO.getTitleId() != null && !requestDTO.getTitleId().isBlank()) {
            Title title = titleRepository.findById(requestDTO.getTitleId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Title not found with ID: " + requestDTO.getTitleId()));
            roysched.setTitle(title);
        }

        return toResponse(royschedRepository.save(roysched));
    }
}