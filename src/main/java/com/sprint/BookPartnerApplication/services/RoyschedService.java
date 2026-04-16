package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.Roysched;
import com.sprint.BookPartnerApplication.repository.RoyschedRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoyschedService {

    private final RoyschedRepository royschedRepository;

    public RoyschedService(RoyschedRepository royschedRepository) {
        this.royschedRepository = royschedRepository;
    }

    public List<Roysched> getAllRoysched() {
        return royschedRepository.findAll();
    }

    public Optional<Roysched> getRoyschedById(Integer id) {
        return royschedRepository.findById(id);
    }

    public Roysched createRoysched(Roysched roysched) {
    	System.out.println("check");
        return royschedRepository.save(roysched);
    }

    public Roysched updateRoysched(Integer id, Roysched royschedDetails) {
        Roysched roysched = royschedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Roysched not found"));

        roysched.setLorange(royschedDetails.getLorange());
        roysched.setHirange(royschedDetails.getHirange());
        roysched.setRoyalty(royschedDetails.getRoyalty());
        roysched.setTitle(royschedDetails.getTitle());

        return royschedRepository.save(roysched);
    }

    public void deleteRoysched(Integer id) {
        royschedRepository.deleteById(id);
    }
}