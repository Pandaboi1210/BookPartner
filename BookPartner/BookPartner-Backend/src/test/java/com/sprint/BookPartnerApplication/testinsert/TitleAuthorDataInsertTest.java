package com.sprint.BookPartnerApplication.testinsert;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.sprint.BookPartnerApplication.dto.request.TitleAuthorRequestDTO;
import com.sprint.BookPartnerApplication.services.TitleAuthorService;

@SpringBootTest
@Transactional
@Rollback(false)
public class TitleAuthorDataInsertTest {

    @Autowired private TitleAuthorService titleAuthorService;

    @FunctionalInterface
    interface InsertAction { void execute(); }

    private void safeInsert(InsertAction action) {
        try { action.execute(); } catch (Exception e) { /* Already exists, skip */ }
    }

    private void insertTitleAuthor(String auId, String titleId, int auOrd, int royaltyper) {
        safeInsert(() -> {
            TitleAuthorRequestDTO dto = new TitleAuthorRequestDTO();
            dto.setAuId(auId);
            dto.setTitleId(titleId);
            dto.setAuOrd((byte) auOrd);
            dto.setRoyaltyper(royaltyper);
            titleAuthorService.createTitleAuthor(dto);
        });
    }

    @Test
	public
    void insertTitleAuthors() {
        insertTitleAuthor("409-56-7008", "BU1032", 1, 60);
        insertTitleAuthor("486-29-1786", "PS7777", 1, 100);
        insertTitleAuthor("712-45-1867", "MC2222", 1, 100);
        insertTitleAuthor("172-32-1176", "PS3333", 1, 100);
        insertTitleAuthor("213-46-8915", "BU1032", 2, 40);
        insertTitleAuthor("238-95-7766", "PC1035", 1, 100);
        insertTitleAuthor("213-46-8915", "BU2075", 1, 100);
        insertTitleAuthor("998-72-3567", "PS2091", 1, 50);
        insertTitleAuthor("899-46-2035", "PS2091", 2, 50);
        insertTitleAuthor("998-72-3567", "PS2106", 1, 100);
        insertTitleAuthor("722-51-5454", "MC3021", 1, 75);
        insertTitleAuthor("899-46-2035", "MC3021", 2, 25);
        insertTitleAuthor("807-91-6654", "TC3218", 1, 100);
        insertTitleAuthor("274-80-9391", "BU7832", 1, 100);
        insertTitleAuthor("427-17-2319", "PC8888", 1, 50);
        insertTitleAuthor("846-92-7186", "PC8888", 2, 50);
        insertTitleAuthor("756-30-7391", "PS1372", 1, 75);
        insertTitleAuthor("724-80-9391", "PS1372", 2, 25);
        insertTitleAuthor("724-80-9391", "BU1111", 1, 60);
        insertTitleAuthor("267-41-2394", "BU1111", 2, 40);
        insertTitleAuthor("672-71-3249", "TC7777", 1, 40);
        insertTitleAuthor("267-41-2394", "TC7777", 2, 30);
        insertTitleAuthor("472-27-2349", "TC7777", 3, 30);
        insertTitleAuthor("648-92-1872", "TC4203", 1, 100);
        System.out.println("TitleAuthors inserted.");
    }
}
