package com.sprint.BookPartnerApplication.testinsert;

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

    @Autowired private EmployeeService employeeService;

    @FunctionalInterface
    interface InsertAction { void execute(); }

    private void safeInsert(InsertAction action) {
        try { action.execute(); } catch (Exception e) { /* Already exists, skip */ }
    }

    private void insertEmployee(String empId, String fname, String lname,
                                short jobId, int jobLvl, String pubId) {
        safeInsert(() -> {
            EmployeeRequestDTO dto = new EmployeeRequestDTO();
            dto.setEmpId(empId);
            dto.setFname(fname);
            dto.setLname(lname);
            dto.setJobId(jobId);
            dto.setJobLvl(jobLvl);
            dto.setPubId(pubId);
            employeeService.createEmployee(dto);
        });
    }

    @Test
	public
    void insertEmployees() {
        insertEmployee("PTC11962M", "Philip",    "Cramer",    (short)  2, 215, "9952");
        insertEmployee("AMD15433F", "Ann",       "Devon",     (short)  3, 200, "9952");
        insertEmployee("F-C16315M", "Francisco", "Chang",     (short)  4, 227, "9952");
        insertEmployee("LAL21447M", "Laurence",  "Lebihan",   (short)  5, 175, "0736");
        insertEmployee("PXH22250M", "Paul",      "Henriot",   (short)  5, 159, "0877");
        insertEmployee("SKO22412M", "Sven",      "Ottlieb",   (short)  5, 150, "1389");
        insertEmployee("RBM23061F", "Rita",      "Muller",    (short)  5, 198, "1622");
        insertEmployee("MJP25939M", "Maria",     "Pontes",    (short)  5, 246, "1756");
        insertEmployee("JYL26161F", "Janine",    "Labrune",   (short)  5, 172, "9901");
        insertEmployee("CFH28514M", "Carlos",    "Hernadez",  (short)  5, 211, "9999");
        insertEmployee("VPA30890F", "Victoria",  "Ashworth",  (short)  6, 140, "0877");
        insertEmployee("L-B31947F", "Lesley",    "Brown",     (short)  7, 120, "0877");
        insertEmployee("ARD36773F", "Anabela",   "Domingues", (short)  8, 100, "0877");
        insertEmployee("M-R38834F", "Martine",   "Rance",     (short)  9,  75, "0877");
        insertEmployee("PHF38899M", "Peter",     "Franken",   (short) 10,  75, "0877");
        insertEmployee("DBT39435M", "Daniel",    "Tonini",    (short) 11,  75, "0877");
        insertEmployee("H-B39728F", "Helen",     "Bennett",   (short) 12,  35, "0877");
        insertEmployee("PMA42628M", "Paolo",     "Accorti",   (short) 13,  35, "0877");
        insertEmployee("ENL44273F", "Elizabeth", "Lincoln",   (short) 14,  35, "0877");
        insertEmployee("MGK44605M", "Matti",     "Karttunen", (short)  6, 220, "0736");
        insertEmployee("PDI47470M", "Palle",     "Ibsen",     (short)  7, 195, "0736");
        insertEmployee("MMS49649F", "Mary",      "Saveley",   (short)  8, 175, "0736");
        insertEmployee("GHT50241M", "Gary",      "Thomas",    (short)  9, 170, "0736");
        insertEmployee("MFS52347M", "Martin",    "Sommer",    (short) 10, 165, "0736");
        insertEmployee("R-M53550M", "Roland",    "Mendel",    (short) 11, 150, "0736");
        insertEmployee("HAS54740M", "Howard",    "Snyder",    (short) 12, 100, "0736");
        insertEmployee("TPO55093M", "Timothy",   "O'Rourke",  (short) 13, 100, "0736");
        insertEmployee("KFJ64308F", "Karin",     "Josephs",   (short) 14, 100, "0736");
        insertEmployee("DWR65030M", "Diego",     "Roel",      (short)  6, 192, "1389");
        insertEmployee("M-L67958F", "Maria",     "Larsson",   (short)  7, 135, "1389");
        insertEmployee("PSP68661F", "Paula",     "Parente",   (short)  8, 125, "1389");
        insertEmployee("MAS70474F", "Margaret",  "Smith",     (short)  9,  78, "1389");
        insertEmployee("A-C71970F", "Aria",      "Cruz",      (short) 10,  87, "1389");
        insertEmployee("MAP77183M", "Miguel",    "Paolino",   (short) 11, 112, "1389");
        insertEmployee("Y-L77953M", "Yoshi",     "Latimer",   (short) 12,  32, "1389");
        insertEmployee("CGS88322F", "Carine",    "Schmitt",   (short) 13,  64, "1389");
        insertEmployee("PSA89086M", "Pedro",     "Afonso",    (short) 14,  89, "1389");
        insertEmployee("A-R89858F", "Annette",   "Roulet",    (short)  6, 152, "9999");
        insertEmployee("HAN90777M", "Helvetius", "Nagy",      (short)  7, 120, "9999");
        insertEmployee("M-P91209M", "Manuel",    "Pereira",   (short)  8, 101, "9999");
        insertEmployee("KJJ92907F", "Karla",     "Jablonski", (short)  9, 170, "9999");
        insertEmployee("POK93028M", "Pirkko",    "Koskitalo", (short) 10,  80, "9999");
        insertEmployee("PCM98509F", "Patricia",  "McKenna",   (short) 11, 150, "9999");
        System.out.println("Employees inserted.");
    }
}
