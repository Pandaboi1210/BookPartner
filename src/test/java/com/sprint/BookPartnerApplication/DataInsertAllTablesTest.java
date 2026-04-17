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

    @Test
    void insertAllData() {

        System.out.println("Starting data insertion...");
        
        // Generate unique identifiers using current time to avoid conflicts
        long timestamp = System.currentTimeMillis();
        String uniqueNum = String.valueOf(timestamp % 10000); // 4 digit unique number
        
        // Pad with zeros to ensure 4 characters for stor_id
        while (uniqueNum.length() < 4) {
            uniqueNum = "0" + uniqueNum;
        }

        /*
        ---------- AUTHORS ----------
        */
        AuthorsRequestDTO author = new AuthorsRequestDTO();
        author.setAuId("999-" + uniqueNum.substring(0, 2) + "-" + uniqueNum.substring(2));
        author.setAuFname("Raj");
        author.setAuLname("Kumar");
        author.setPhone("1234567890");
        author.setCity("Chennai");
        author.setState("TN");
        author.setZip("60001");
        author.setContract(1);

        authorsService.createAuthor(author);

        /*
        ---------- PUBLISHERS ----------
        */
        PublishersRequestDTO publisher = new PublishersRequestDTO();
        publisher.setPubId("99" + uniqueNum.substring(0, 2));
        publisher.setPubName("TechBooks");
        publisher.setCity("Chennai");
        publisher.setState("TN");

        publishersService.createPublisher(publisher);

        /*
        ---------- JOBS ----------
        */
        JobsRequestDTO job = new JobsRequestDTO();
        job.setJobId((short) (100 + Integer.parseInt(uniqueNum) % 100));
        job.setJobDesc("Manager");
        job.setMinLvl(10);
        job.setMaxLvl(100);

        jobsService.createJob(job);

        /*
        ---------- STORE ----------
        */
        StoreRequestDTO store = new StoreRequestDTO();
        store.setStorId(uniqueNum); // Uses 4-digit ID to fit in database field
        store.setStorName("Main Store");
        store.setCity("Chennai");
        store.setState("TN");

        storeService.createStore(store);

        /*
        ---------- TITLE ----------
        */
        TitleRequestDTO title = new TitleRequestDTO();
        title.setTitleId("T" + uniqueNum);
        title.setTitle("Spring Boot Guide");
        title.setType("Business");
        title.setPrice(500.0);
        title.setAdvance(1000.0);
        title.setRoyalty(10);
        title.setPubdate(LocalDateTime.now());

        titleService.insertTitle(title);

        /*
        ---------- EMPLOYEE ----------
        */
        EmployeeRequestDTO employee = new EmployeeRequestDTO();
        employee.setEmpId("AAA" + uniqueNum + "M");
        employee.setFname("John");
        employee.setLname("Doe");
        employee.setJobId((short) (100 + Integer.parseInt(uniqueNum) % 100));
        employee.setPubId("99" + uniqueNum.substring(0, 2));
        employee.setJobLvl(5);

        employeeService.createEmployee(employee);

        /*
        ---------- DISCOUNTS ----------
        */
        DiscountRequestDTO discount = new DiscountRequestDTO();
        discount.setDiscounttype("Bulk");
        discount.setLowqty(10);
        discount.setHighqty(50);
        discount.setDiscount(new BigDecimal("0.10"));
        discount.setStorId(uniqueNum); // Uses same 4-digit ID as store

        discountsService.createDiscount(discount);

        /*
        ---------- ROYSCHED ----------
        */
        RoyschedRequestDTO roy = new RoyschedRequestDTO();
        roy.setLorange(0);
        roy.setHirange(1000);
        roy.setRoyalty(10);
        roy.setTitleId("T" + uniqueNum);

        royschedService.createRoysched(roy);

        System.out.println("All data inserted successfully.");
    }
}