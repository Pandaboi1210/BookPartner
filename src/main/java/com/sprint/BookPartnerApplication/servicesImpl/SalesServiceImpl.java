package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.entity.Sales;
import com.sprint.BookPartnerApplication.entity.SalesId;
import com.sprint.BookPartnerApplication.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesServiceImpl {

    @Autowired
    private SalesRepository salesRepository;

    public List<Sales> getAllSales() {
        return salesRepository.findAll();
    }

    public Optional<Sales> getSalesById(SalesId id) {
        return salesRepository.findById(id);
    }

    public Sales saveSales(Sales sales) {
        return salesRepository.save(sales);
    }

    public void deleteSales(SalesId id) {
        salesRepository.deleteById(id);
    }
}