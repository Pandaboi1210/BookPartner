package com.sprint.BookPartnerApplication;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sprint.BookPartnerApplication.entity.*;
import com.sprint.BookPartnerApplication.services.*;

@SpringBootTest
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

        /*
        ---------- AUTHORS ----------
        */
        Authors author = new Authors();
        author.setAuId("A001");
        author.setAuFname("Raj");
        author.setAuLname("Kumar");
        author.setPhone("1234567890");
        author.setCity("Chennai");
        author.setState("TN");
        author.setZip("600001");
        author.setContract(1);

        authorsService.createAuthor(author);

        /*
        ---------- PUBLISHERS ----------
        */
        Publishers publisher = new Publishers();
        publisher.setPubId("P001");
        publisher.setPubName("TechBooks");
        publisher.setCity("Chennai");
        publisher.setState("TN");

        publishersService.createPublisher(publisher);

        /*
        ---------- JOBS ----------
        */
        Jobs job = new Jobs();
        job.setJobDesc("Manager");
        job.setMinLvl(10);
        job.setMaxLvl(100);

        jobsService.createJob(job);

        /*
        ---------- STORE ----------
        */
        Store store = new Store();
        store.setStorId("S001");
        store.setStorName("Main Store");
        store.setCity("Chennai");
        store.setState("TN");

        storeService.createStore(store);

        /*
        ---------- TITLE ----------
        */
        Title title = new Title();
        title.setTitleId("T001");
        title.setTitle("Spring Boot Guide");
        title.setType("Business");
        title.setPublisher(publisher);
        title.setPrice(500.0);
        title.setAdvance(1000.0);
        title.setRoyalty(10);
        title.setPubdate(LocalDateTime.now());

        titleService.insertTitle(title);

        /*
        ---------- EMPLOYEE ----------
        */
        Employee employee = new Employee();
        employee.setEmpId("AAA10001M");
        employee.setFname("John");
        employee.setLname("Doe");
        employee.setJob(job);
        employee.setPubId("P001");
        employee.setHireDate(LocalDateTime.now());

        employeeService.createEmployee(employee);

        /*
        ---------- DISCOUNTS ----------
        */
        Discounts discount = new Discounts();
        discount.setDiscounttype("Bulk");
        discount.setLowqty(10);
        discount.setHighqty(50);
        discount.setDiscount(new BigDecimal("0.10"));
        discount.setStore(store);

        discountsService.createDiscount(discount);

        /*
        ---------- ROYSCHED ----------
        */
        Roysched roy = new Roysched();
        roy.setLorange(0);
        roy.setHirange(1000);
        roy.setRoyalty(10);
        roy.setTitle(title);

        royschedService.createRoysched(roy);

        System.out.println("All data inserted successfully.");
    }
}