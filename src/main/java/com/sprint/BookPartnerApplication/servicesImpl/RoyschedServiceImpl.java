package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.Roysched;
import com.sprint.BookPartnerApplication.entity.Title;
import com.sprint.BookPartnerApplication.exception.BadRequestException;
import com.sprint.BookPartnerApplication.exception.DuplicateResourceException; // 🚨 Added import
import com.sprint.BookPartnerApplication.exception.InvalidInputException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
import com.sprint.BookPartnerApplication.repository.RoyschedRepository;
import com.sprint.BookPartnerApplication.repository.TitleRepository;
import com.sprint.BookPartnerApplication.services.RoyschedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoyschedServiceImpl implements RoyschedService {

    @Autowired
    private RoyschedRepository royschedRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Override
    public Roysched createRoysched(Roysched roysched) {
        
        if (roysched.getTitle() == null || roysched.getTitle().getTitleId() == null || roysched.getTitle().getTitleId().isBlank()) {
            throw new BadRequestException("A valid Title ID is required to create a royalty schedule.");
        }

        Title title = titleRepository.findById(roysched.getTitle().getTitleId())
                .orElseThrow(() -> new ResourceNotFoundException("Title not found with ID: " + roysched.getTitle().getTitleId()));
        
        roysched.setTitle(title);

        if (roysched.getLorange() != null && roysched.getHirange() != null) {
            if (roysched.getLorange() > roysched.getHirange()) {
                throw new InvalidInputException("Low range (lorange) cannot be greater than high range (hirange).");
            }
        }

        // 🚨 409 CONFLICT: Prevent duplicate royalty ranges for the same book!
        if (royschedRepository.existsByTitle_TitleIdAndLorangeAndHirange(
                title.getTitleId(), roysched.getLorange(), roysched.getHirange())) {
            throw new DuplicateResourceException("A royalty schedule with this exact low and high range already exists for this title.");
        }

        return royschedRepository.save(roysched);
    }

    @Override
    public List<Roysched> getRoyschedByTitle(String titleId) {
        List<Roysched> schedules = royschedRepository.findByTitle_TitleId(titleId);
        
        if (schedules.isEmpty()) {
            throw new ResourceNotFoundException("No royalty schedules found for title ID: " + titleId);
        }
        return schedules;
    }

    @Override
    public Roysched updateRoysched(Integer royaltyId, Roysched royschedDetails) {
        
        Roysched roysched = royschedRepository.findById(royaltyId)
                .orElseThrow(() -> new ResourceNotFoundException("Royalty slab not found with id: " + royaltyId));

        if (royschedDetails.getLorange() != null && royschedDetails.getHirange() != null) {
            if (royschedDetails.getLorange() > royschedDetails.getHirange()) {
                throw new InvalidInputException("Low range (lorange) cannot be greater than high range (hirange).");
            }
        }

        roysched.setLorange(royschedDetails.getLorange());
        roysched.setHirange(royschedDetails.getHirange());
        roysched.setRoyalty(royschedDetails.getRoyalty());

        return royschedRepository.save(roysched);
    }
}