package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.Roysched;
import com.sprint.BookPartnerApplication.repository.RoyschedRepository;
import com.sprint.BookPartnerApplication.services.RoyschedService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoyschedServiceImpl implements RoyschedService {

    private final RoyschedRepository royschedRepository;

    public RoyschedServiceImpl(RoyschedRepository royschedRepository) {
        this.royschedRepository = royschedRepository;
    }

    @Override
    public Roysched createRoysched(Roysched roysched) {
        return royschedRepository.save(roysched);
    }

    @Override
    public List<Roysched> getRoyschedByTitle(String titleId) {
        return royschedRepository.findByTitle_TitleId(titleId);
    }

    @Override
    public Roysched updateRoysched(Integer royaltyId, Roysched royschedDetails) {
        Roysched roysched = royschedRepository.findById(royaltyId)
                .orElseThrow(() -> new RuntimeException("Royalty slab not found with id: " + royaltyId));

        roysched.setLorange(royschedDetails.getLorange());
        roysched.setHirange(royschedDetails.getHirange());
        roysched.setRoyalty(royschedDetails.getRoyalty());
        roysched.setTitle(royschedDetails.getTitle());

        return royschedRepository.save(roysched);
    }
}