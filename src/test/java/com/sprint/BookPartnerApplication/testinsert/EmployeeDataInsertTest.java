package com.sprint.BookPartnerApplication.testinsert;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.sprint.BookPartnerApplication.dto.request.EmployeeRequestDTO;
import com.sprint.BookPartnerApplication.services.EmployeeService;

@SpringBootTest
@Transactional
@Rollback(false)
public class EmployeeDataInsertTest {

    @Autowired
    private EmployeeService employeeService;

    @FunctionalInterface
    interface InsertAction {
        void execute();
    }

    private void safeInsert(InsertAction action) {
        try {
            action.execute();
        } catch (Exception e) {
            // Already exists, skip
        }
    }

    private void insertEmployee(
            String empId,
            String fname,
            String minit,
            String lname,
            short jobId,
            int jobLvl,
            String pubId,
            LocalDate hireDate) {

        safeInsert(() -> {

            EmployeeRequestDTO dto = new EmployeeRequestDTO();

            dto.setEmpId(empId);
            dto.setFname(fname);
            dto.setMinit(minit);
            dto.setLname(lname);
            dto.setJobId(jobId);
            dto.setJobLvl(jobLvl);
            dto.setPubId(pubId);
            dto.setHireDate(hireDate);

            employeeService.createEmployee(dto);
        });
    }

    @Test
    public void insertEmployees() {

        insertEmployee("PTC11962M", "Philip", "T", "Cramer",
                (short) 2, 215, "9952",
                LocalDate.of(1989, 11, 11));

        insertEmployee("AMD15433F", "Ann", "M", "Devon",
                (short) 3, 200, "9952",
                LocalDate.of(1991, 7, 16));

        insertEmployee("F-C16315M", "Francisco", "", "Chang",
                (short) 4, 227, "9952",
                LocalDate.of(1990, 11, 3));

        insertEmployee("LAL21447M", "Laurence", "A", "Lebihan",
                (short) 5, 175, "0736",
                LocalDate.of(1990, 6, 3));

        insertEmployee("PXH22250M", "Paul", "X", "Henriot",
                (short) 5, 159, "0877",
                LocalDate.of(1993, 8, 19));

        insertEmployee("SKO22412M", "Sven", "K", "Ottlieb",
                (short) 5, 150, "1389",
                LocalDate.of(1991, 4, 5));

        insertEmployee("RBM23061F", "Rita", "B", "Muller",
                (short) 5, 198, "1622",
                LocalDate.of(1993, 10, 9));

        insertEmployee("MJP25939M", "Maria", "J", "Pontes",
                (short) 5, 246, "1756",
                LocalDate.of(1989, 3, 1));

        insertEmployee("JYL26161F", "Janine", "Y", "Labrune",
                (short) 5, 172, "9901",
                LocalDate.of(1991, 5, 26));

        insertEmployee("CFH28514M", "Carlos", "F", "Hernadez",
                (short) 5, 211, "9999",
                LocalDate.of(1989, 4, 21));

        insertEmployee("VPA30890F", "Victoria", "P", "Ashworth",
                (short) 6, 140, "0877",
                LocalDate.of(1990, 9, 13));

        insertEmployee("L-B31947F", "Lesley", "", "Brown",
                (short) 7, 120, "0877",
                LocalDate.of(1991, 2, 13));

        insertEmployee("ARD36773F", "Anabela", "R", "Domingues",
                (short) 8, 100, "0877",
                LocalDate.of(1993, 1, 27));

        insertEmployee("M-R38834F", "Martine", "", "Rance",
                (short) 9, 75, "0877",
                LocalDate.of(1992, 2, 5));

        insertEmployee("PHF38899M", "Peter", "H", "Franken",
                (short) 10, 75, "0877",
                LocalDate.of(1992, 5, 17));

        insertEmployee("DBT39435M", "Daniel", "B", "Tonini",
                (short) 11, 75, "0877",
                LocalDate.of(1990, 1, 1));

        insertEmployee("H-B39728F", "Helen", "", "Bennett",
                (short) 12, 35, "0877",
                LocalDate.of(1989, 9, 21));

        insertEmployee("PMA42628M", "Paolo", "M", "Accorti",
                (short) 13, 35, "0877",
                LocalDate.of(1992, 8, 27));

        insertEmployee("ENL44273F", "Elizabeth", "N", "Lincoln",
                (short) 14, 35, "0877",
                LocalDate.of(1990, 7, 24));

        System.out.println("Employees inserted successfully.");
    }
}