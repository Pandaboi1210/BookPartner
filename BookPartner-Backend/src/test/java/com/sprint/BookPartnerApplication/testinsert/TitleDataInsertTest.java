package com.sprint.BookPartnerApplication.testinsert;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.sprint.BookPartnerApplication.dto.request.TitleRequestDTO;
import com.sprint.BookPartnerApplication.services.TitleService;

@SpringBootTest
@Transactional
@Rollback(false)
public class TitleDataInsertTest {

    @Autowired private TitleService titleService;

    @FunctionalInterface
    interface InsertAction { void execute(); }

    private void safeInsert(InsertAction action) {
        try { action.execute(); } catch (Exception e) { /* Already exists, skip */ }
    }

    private void insertTitle(String id, String title, String type,
                             String pubId, double price, double advance,
                             int royalty, int ytdSales, String notes, String pubdate) {
        safeInsert(() -> {
            TitleRequestDTO dto = new TitleRequestDTO();
            dto.setTitleId(id);
            dto.setTitle(title);
            dto.setType(type);
            dto.setPubId(pubId);
            dto.setPrice(price);
            dto.setAdvance(advance);
            dto.setRoyalty(royalty);
            dto.setYtdSales(ytdSales);
            dto.setNotes(notes);
            dto.setPubdate(LocalDateTime.parse(pubdate + "T00:00:00"));
            titleService.insertTitle(dto);
        });
    }

    @Test
	public
    void insertTitles() {
        insertTitle("PC8888", "Secrets of Silicon Valley",                                       "popular_comp", "1389", 20.00,  8000.00, 10, 4095,  "Muckraking reporting on the worlds largest computer hardware and software manufacturers.",                                                                                           "1994-06-12");
        insertTitle("BU1032", "The Busy Executive's Database Guide",                             "business",     "1389", 19.99,  5000.00, 10, 4095,  "An overview of available database systems with emphasis on common business applications. Illustrated.",                                                                                "1991-06-12");
        insertTitle("PS7777", "Emotional Security: A New Algorithm",                             "psychology",   "0736",  7.99,  4000.00, 10, 3336,  "Protecting yourself and your loved ones from undue emotional stress in the modern world. Use of computer and nutritional aids emphasized.",                                           "1991-06-12");
        insertTitle("PS3333", "Prolonged Data Deprivation: Four Case Studies",                   "psychology",   "0736", 19.99,  2000.00, 10, 4072,  "What happens when the data runs dry? Searching evaluations of information-shortage effects.",                                                                                         "1991-06-12");
        insertTitle("BU1111", "Cooking with Computers: Surreptitious Balance Sheets",            "business",     "1389", 11.95,  5000.00, 10, 3876,  "Helpful hints on how to use your electronic resources to the best advantage.",                                                                                                      "1991-06-09");
        insertTitle("MC2222", "Silicon Valley Gastronomic Treats",                               "mod_cook",     "0877", 19.99,     0.00, 12, 2032,  "Favorite recipes for quick, easy, and elegant meals.",                                                                                                                              "1991-06-09");
        insertTitle("TC7777", "Sushi, Anyone?",                                                  "trad_cook",    "0877", 14.99,  8000.00, 10, 4095,  "Detailed instructions on how to make authentic Japanese sushi in your spare time.",                                                                                                   "1991-06-12");
        insertTitle("TC4203", "Fifty Years in Buckingham Palace Kitchens",                       "trad_cook",    "0877", 11.95,  4000.00, 14, 15096, "More anecdotes from the Queen's favorite cook describing life among English royalty. Recipes, techniques, tender vignettes.",                                                        "1991-06-12");
        insertTitle("PC1035", "But Is It User Friendly?",                                        "popular_comp", "1389", 22.95,  7000.00, 16, 8780,  "A survey of software for the naive user, focusing on the friendliness of each.",                                                                                                     "1991-06-30");
        insertTitle("BU2075", "You Can Combat Computer Stress!",                                 "business",     "0736",  2.99, 10125.00, 24, 18722, "The latest medical and psychological techniques for living with the electronic office. Easy-to-understand explanations.",                                                              "1991-06-30");
        insertTitle("PS2091", "Is Anger the Enemy?",                                             "psychology",   "0736", 10.95,  2275.00, 12, 2045,  "Carefully researched study of the effects of strong emotions on the body. Metabolic charts included.",                                                                                "1991-06-15");
        insertTitle("PS2106", "Life Without Fear",                                               "psychology",   "0736",  7.00,  6000.00, 10, 111,   "New exercise, meditation, and nutritional techniques that can reduce the shock of daily interactions. Popular audience. Sample menus included, exercise video available separately.", "1991-10-05");
        insertTitle("MC3021", "The Gourmet Microwave",                                           "mod_cook",     "0877",  2.99, 15000.00, 24, 22246, "Traditional French gourmet recipes adapted for modern microwave cooking.",                                                                                                           "1991-06-18");
        insertTitle("TC3218", "Onions, Leeks, and Garlic: Cooking Secrets of the Mediterranean", "trad_cook",    "0877", 20.95,  7000.00, 10, 375,   "Profusely illustrated in color, this makes a wonderful gift book for a cuisine-oriented friend.",                                                                                     "1991-10-21");
        insertTitle("BU7832", "Straight Talk About Computers",                                   "business",     "1389", 19.99,  5000.00, 10, 4095,  "Annotated analysis of what computers can do for you: a no-hype guide for the critical user.",                                                                                        "1991-06-22");
        insertTitle("PS1372", "Computer Phobic AND Non-Phobic Individuals: Behavior Variations", "psychology",   "0877", 21.59,  7000.00, 10, 375,   "A must for the specialist, this book examines the difference between those who hate and fear computers and those who don't.",                                                          "1991-10-21");
        System.out.println("Titles inserted.");
    }
}
