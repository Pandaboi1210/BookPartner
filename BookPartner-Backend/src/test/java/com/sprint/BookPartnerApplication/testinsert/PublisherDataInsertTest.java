package com.sprint.BookPartnerApplication.testinsert;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sprint.BookPartnerApplication.dto.request.PublishersRequestDTO;
import com.sprint.BookPartnerApplication.repository.PublishersRepository;
import com.sprint.BookPartnerApplication.services.PublisherService;

@SpringBootTest
@Order(2)
public class PublisherDataInsertTest {

    @Autowired private PublisherService publishersService;
    @Autowired private PublishersRepository publishersRepository;

    @FunctionalInterface
    interface InsertAction { void execute(); }

    private void safeInsert(InsertAction action) {
        try { action.execute(); } catch (Exception e) { /* Already exists, skip */ }
    }

    private void insertPublisher(String id, String name, String city,
                                 String state, String country) {
        if (publishersRepository.existsById(id)) return;
        safeInsert(() -> {
            PublishersRequestDTO dto = new PublishersRequestDTO();
            dto.setPubId(id);
            dto.setPubName(name);
            dto.setCity(city);
            dto.setState(state);
            dto.setCountry(country);
            publishersService.createPublisher(dto);
        });
    }

    @Test
    public void insertPublishers() {
        if (publishersRepository.count() >= 10) {
            System.out.println("Already have " + publishersRepository.count() + " publishers. Skipping insertion.");
            return;
        }

        insertPublisher("0736", "New Moon Books",        "Boston",       "MA", "USA");
        insertPublisher("0877", "Binnet & Hardley",      "Washington",   "DC", "USA");
        insertPublisher("1389", "Algodata Infosystems",  "Berkeley",     "CA", "USA");
        insertPublisher("9952", "Scootney Books",        "New York",     "NY", "USA");
        insertPublisher("1622", "Five Lakes Publishing", "Chicago",      "IL", "USA");
        insertPublisher("1756", "Ramona Publishers",     "Dallas",       "TX", "USA");
        insertPublisher("9901", "GGG&G",                 "Munchen",      "BY", "Germany");
        insertPublisher("9999", "Lucerne Publishing",    "Paris",        "IF", "France");
        insertPublisher("1234", "Sunrise Publishing",    "San Diego",    "CA", "USA");
        insertPublisher("5678", "Oceanic Press",         "Honolulu",     "HI", "USA");
        System.out.println("Publishers insertion completed. Total count: " + publishersRepository.count());
    }
}
