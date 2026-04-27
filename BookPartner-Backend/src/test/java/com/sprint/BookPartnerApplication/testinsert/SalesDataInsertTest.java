package com.sprint.BookPartnerApplication.testinsert;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sprint.BookPartnerApplication.dto.request.SalesRequestDTO;
import com.sprint.BookPartnerApplication.entity.SalesId;
import com.sprint.BookPartnerApplication.repository.SalesRepository;
import com.sprint.BookPartnerApplication.services.SalesService;

@SpringBootTest
@Order(10)
public class SalesDataInsertTest {

    @Autowired private SalesService salesService;
    @Autowired private SalesRepository salesRepository;

    @FunctionalInterface
    interface InsertAction { void execute(); }

    private void safeInsert(InsertAction action) {
        try { action.execute(); } catch (Exception e) { /* Already exists, skip */ }
    }

    private void insertSale(String storId, String ordNum, String ordDate,
                            int qty, String payterms, String titleId) {
        try {
            SalesId salesId = new SalesId(storId, ordNum, titleId);
            if (salesRepository.existsById(salesId)) return;
        } catch (Exception e) { return; }
        safeInsert(() -> {
            SalesRequestDTO dto = new SalesRequestDTO();
            dto.setStorId(storId);
            dto.setOrdNum(ordNum);
            dto.setOrdDate(LocalDateTime.parse(ordDate + "T00:00:00"));
            dto.setQty((short) qty);
            dto.setPayterms(payterms);
            dto.setTitleId(titleId);
            salesService.createSale(dto);
        });
    }

    @Test
    public void insertSales() {
        if (salesRepository.count() >= 10) {
            System.out.println("Already have " + salesRepository.count() + " sales. Skipping insertion.");
            return;
        }

        insertSale("7066", "QA7442.3", "1994-09-13", 75, "ON invoice", "PS2091");
        insertSale("7067", "D4482",    "1994-09-14", 10, "Net 60",     "PS2091");
        insertSale("7131", "N914008",  "1994-09-14", 20, "Net 30",     "PS2091");
        insertSale("7131", "N914014",  "1994-09-14", 25, "Net 30",     "MC3021");
        insertSale("8042", "423LL922", "1994-09-14", 15, "ON invoice", "MC3021");
        insertSale("8042", "423LL930", "1994-09-14", 10, "ON invoice", "BU1032");
        insertSale("6380", "722a",     "1994-09-13",  3, "Net 60",     "PS2091");
        insertSale("6380", "6871",     "1994-09-14",  5, "Net 60",     "BU1032");
        insertSale("8042", "P723",     "1993-03-11", 25, "Net 30",     "BU1111");
        insertSale("7896", "X999",     "1993-02-21", 35, "ON invoice", "BU2075");
        insertSale("7896", "QQ2299",   "1993-10-28", 15, "Net 60",     "BU7832");
        insertSale("7896", "TQ456",    "1993-12-12", 10, "Net 60",     "MC2222");
        insertSale("8042", "QA879.1",  "1993-05-22", 30, "Net 30",     "PC1035");
        insertSale("7066", "A2976",    "1993-05-24", 50, "Net 30",     "PC8888");
        insertSale("7131", "P3087a",   "1993-05-29", 20, "Net 60",     "PS1372");
        insertSale("7131", "P3087a",   "1993-05-29", 25, "Net 60",     "PS2106");
        insertSale("7131", "P3087a",   "1993-05-29", 15, "Net 60",     "PS3333");
        insertSale("7131", "P3087a",   "1993-05-29", 25, "Net 60",     "PS7777");
        insertSale("7067", "P2121",    "1992-06-15", 40, "Net 30",     "TC3218");
        insertSale("7067", "P2121",    "1992-06-15", 20, "Net 30",     "TC4203");
        insertSale("7067", "P2121",    "1992-06-15", 20, "Net 30",     "TC7777");
        System.out.println("Sales insertion completed. Total count: " + salesRepository.count());
    }
}
