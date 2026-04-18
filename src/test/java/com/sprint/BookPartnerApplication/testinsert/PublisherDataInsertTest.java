package com.sprint.BookPartnerApplication.testinsert;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.sprint.BookPartnerApplication.dto.request.PublishersRequestDTO;
import com.sprint.BookPartnerApplication.services.PublisherService;

@SpringBootTest
@Transactional
@Rollback(false)
public class PublisherDataInsertTest {

    @Autowired private PublisherService publishersService;

    @FunctionalInterface
    interface InsertAction { void execute(); }

    private void safeInsert(InsertAction action) {
        try { action.execute(); } catch (Exception e) { /* Already exists, skip */ }
    }

    private void insertPublisher(String id, String name, String city,
                                 String state, String country) {
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
	public
    void insertPublishers() {
        insertPublisher("0736", "New Moon Books",        "Boston",     "MA", "USA");
        insertPublisher("0877", "Binnet & Hardley",      "Washington", "DC", "USA");
        insertPublisher("1389", "Algodata Infosystems",  "Berkeley",   "CA", "USA");
        insertPublisher("9952", "Scootney Books",        "New York",   "NY", "USA");
        insertPublisher("1622", "Five Lakes Publishing", "Chicago",    "IL", "USA");
        insertPublisher("1756", "Ramona Publishers",     "Dallas",     "TX", "USA");
        insertPublisher("9901", "GGG&G",                 "Munchen",    null, "Germany");
        insertPublisher("9999", "Lucerne Publishing",    "Paris",      null, "France");
        System.out.println("Publishers inserted.");
    }
}
