package com.sprint.BookPartnerApplication;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;

import com.sprint.BookPartnerApplication.dto.request.*;
import com.sprint.BookPartnerApplication.services.*;

@SpringBootTest
@Transactional
@Rollback(false)
public class DataInsertAllTablesTest {

    @Autowired
    private AuthorsService authorsService;

    @Autowired
    private PublisherService publishersService;

    @Autowired
    private JobsService jobsService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private TitleService titleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DiscountService discountsService;

    @Autowired
    private RoyschedService royschedService;

    @Autowired
    private SalesService salesService;

    @Autowired
    private TitleAuthorService titleAuthorService;

    @Test
    void insertAllData() {

        System.out.println("Starting data insertion...");

        // Generate unique identifiers using current time to avoid conflicts
        long timestamp = System.currentTimeMillis();
        String uniqueNum = String.valueOf(timestamp % 10000); // 4 digit unique number

        // Pad with zeros to ensure 4 characters
        while (uniqueNum.length() < 4) {
            uniqueNum = "0" + uniqueNum;
        }

        /*
        ---------- AUTHORS ----------
        Pattern: XXX-XX-XXXX
        e.g. "999-12-3412"
        */
        AuthorsRequestDTO author = new AuthorsRequestDTO();
        author.setAuId("999-" + uniqueNum.substring(0, 2) + "-" + uniqueNum); // ✅ fixed: last segment is 4 digits
        author.setAuFname("Raj");
        author.setAuLname("Kumar");
        author.setPhone("1234567890");
        author.setCity("Chennai");
        author.setState("TN");
        author.setZip("60001");
        author.setContract(1);

        authorsService.createAuthor(author);
        System.out.println("Author inserted.");

        /*
        ---------- PUBLISHERS ----------
        Pattern: known IDs or 99XX
        e.g. "9912"
        */
        PublishersRequestDTO publisher = new PublishersRequestDTO();
        publisher.setPubId("99" + uniqueNum.substring(0, 2)); // ✅ matches 99XX pattern
        publisher.setPubName("TechBooks");
        publisher.setCity("Chennai");
        publisher.setState("TN");
        publisher.setCountry("India");

        publishersService.createPublisher(publisher);
        System.out.println("Publisher inserted.");

        /*
        ---------- JOBS ----------
        minLvl must be >= 10, maxLvl must be <= 250, minLvl <= maxLvl
        */
        JobsRequestDTO job = new JobsRequestDTO();
        job.setJobId((short) (100 + Integer.parseInt(uniqueNum) % 100));
        job.setJobDesc("Manager");
        job.setMinLvl(10);
        job.setMaxLvl(100);

        jobsService.createJob(job);
        System.out.println("Job inserted.");

        /*
        ---------- STORE ----------
        stor_id is char(4)
        */
        StoreRequestDTO store = new StoreRequestDTO();
        store.setStorId(uniqueNum);
        store.setStorName("Main Store");
        store.setStorAddress("123 Main St");
        store.setCity("Chennai");
        store.setState("TN");
        store.setZip("60001");

        storeService.createStore(store);
        System.out.println("Store inserted.");

        /*
        ---------- TITLE ----------
        title_id is varchar(10), "T" + 4 digits = 5 chars, fine
        No pubId set here intentionally (optional FK)
        */
        TitleRequestDTO title = new TitleRequestDTO();
        title.setTitleId("T" + uniqueNum);
        title.setTitle("Spring Boot Guide");
        title.setType("business");
        title.setPubId("99" + uniqueNum.substring(0, 2)); // link to publisher above
        title.setPrice(500.0);
        title.setAdvance(1000.0);
        title.setRoyalty(10);
        title.setYtdSales(0);
        title.setNotes("A great guide.");
        title.setPubdate(LocalDateTime.now());

        titleService.insertTitle(title);
        System.out.println("Title inserted.");

        /*
        ---------- EMPLOYEE ----------
        emp_id pattern: ^[A-Z]{3}[1-9][0-9]{4}[FM]$ or ^[A-Z]-[A-Z][1-9][0-9]{4}[FM]$
        jobLvl must be within job's minLvl(10) - maxLvl(100)
        */
        EmployeeRequestDTO employee = new EmployeeRequestDTO();
        employee.setEmpId("AAA" + uniqueNum + "M"); // e.g. "AAA3412M" — 8 chars, matches [A-Z]{3}[1-9][0-9]{4}[FM]
        employee.setFname("John");
        employee.setLname("Doe");
        employee.setJobId((short) (100 + Integer.parseInt(uniqueNum) % 100));
        employee.setPubId("99" + uniqueNum.substring(0, 2));
        employee.setJobLvl(10); // ✅ fixed: was 5, must be >= minLvl (10)

        employeeService.createEmployee(employee);
        System.out.println("Employee inserted.");

        /*
        ---------- DISCOUNTS ----------
        lowqty < highqty, discount is a positive decimal
        */
        DiscountRequestDTO discount = new DiscountRequestDTO();
        discount.setDiscounttype("Bulk");
        discount.setLowqty(10);
        discount.setHighqty(50);
        discount.setDiscount(new BigDecimal("0.10"));
        discount.setStorId(uniqueNum);

        discountsService.createDiscount(discount);
        System.out.println("Discount inserted.");

        /*
        ---------- ROYSCHED ----------
        lorange < hirange, royalty 0-100
        */
        RoyschedRequestDTO roy = new RoyschedRequestDTO();
        roy.setTitleId("T" + uniqueNum);
        roy.setLorange(0);
        roy.setHirange(1000);
        roy.setRoyalty(10);

        royschedService.createRoysched(roy);
        System.out.println("Roysched inserted.");

        /*
        ---------- SALES ----------
        Composite PK: (stor_id, ord_num, title_id)
        qty must be >= 1
        */
        SalesRequestDTO sale = new SalesRequestDTO();
        sale.setStorId(uniqueNum);
        sale.setOrdNum("ORD" + uniqueNum);
        sale.setTitleId("T" + uniqueNum);
        sale.setOrdDate(LocalDateTime.now());
        sale.setQty((short) 5);
        sale.setPayterms("Net 30");

        salesService.createSale(sale);
        System.out.println("Sale inserted.");

        /*
        ---------- TITLE AUTHOR ----------
        Composite PK: (au_id, title_id)
        royaltyper 0-100, auOrd >= 1
        */
        TitleAuthorRequestDTO titleAuthor = new TitleAuthorRequestDTO();
        titleAuthor.setAuId("999-" + uniqueNum.substring(0, 2) + "-" + uniqueNum);
        titleAuthor.setTitleId("T" + uniqueNum);
        titleAuthor.setAuOrd((byte) 1);
        titleAuthor.setRoyaltyper(100);

        titleAuthorService.createTitleAuthor(titleAuthor);
        System.out.println("TitleAuthor inserted.");

        System.out.println("All data inserted successfully.");
    }
}