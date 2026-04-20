package com.sprint.BookPartnerApplication.testinsert;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.sprint.BookPartnerApplication.dto.request.StoreRequestDTO;
import com.sprint.BookPartnerApplication.services.StoreService;

@SpringBootTest
@Transactional
@Rollback(false)
public class StoreDataInsertTest {

    @Autowired private StoreService storeService;

    @FunctionalInterface
    interface InsertAction { void execute(); }

    private void safeInsert(InsertAction action) {
        try { action.execute(); } catch (Exception e) { /* Already exists, skip */ }
    }

    private void insertStore(String id, String name, String address,
                             String city, String state, String zip) {
        safeInsert(() -> {
            StoreRequestDTO dto = new StoreRequestDTO();
            dto.setStorId(id);
            dto.setStorName(name);
            dto.setStorAddress(address);
            dto.setCity(city);
            dto.setState(state);
            dto.setZip(zip);
            storeService.createStore(dto);
        });
    }

    @Test
	public
    void insertStores() {
        insertStore("7066", "Barnum's",                            "567 Pasadena Ave.",   "Tustin",   "CA", "92789");
        insertStore("7067", "News & Brews",                        "577 First St.",       "Los Gatos","CA", "96745");
        insertStore("7131", "Doc-U-Mat: Quality Laundry and Books","24-A Avogadro Way",   "Remulade", "WA", "98014");
        insertStore("8042", "Bookbeat",                            "679 Carson St.",      "Portland", "OR", "89076");
        insertStore("6380", "Eric the Read Books",                 "788 Catamaugus Ave.", "Seattle",  "WA", "98056");
        insertStore("7896", "Fricative Bookshop",                  "89 Madison St.",      "Fremont",  "CA", "90019");
        System.out.println("Stores inserted.");
    }
}
