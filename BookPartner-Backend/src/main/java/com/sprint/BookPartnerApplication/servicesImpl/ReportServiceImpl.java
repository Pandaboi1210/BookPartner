package com.sprint.BookPartnerApplication.servicesImpl;

import com.sprint.BookPartnerApplication.dto.response.report.AuthorRoyaltyDTO;
import com.sprint.BookPartnerApplication.dto.response.report.SalesByPublisherDTO;
import com.sprint.BookPartnerApplication.dto.response.report.SalesByTitleDTO;
import com.sprint.BookPartnerApplication.dto.response.report.SalesTrendDTO;
import com.sprint.BookPartnerApplication.dto.response.report.TopAuthorDTO;
import com.sprint.BookPartnerApplication.exception.BadRequestException;
import com.sprint.BookPartnerApplication.exception.ResourceNotFoundException;
import com.sprint.BookPartnerApplication.repository.SalesRepository;
import com.sprint.BookPartnerApplication.repository.TitleAuthorRepository;
import com.sprint.BookPartnerApplication.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private TitleAuthorRepository titleAuthorRepository;

    @Override
    public List<SalesByTitleDTO> getSalesByTitle() {
        List<SalesByTitleDTO> result = salesRepository.getSalesByTitle();
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("No sales data found.");
        }
        return result;
    }

    @Override
    public List<SalesByPublisherDTO> getSalesByPublisher() {
        List<SalesByPublisherDTO> result = salesRepository.getSalesByPublisher();
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("No sales data found.");
        }
        return result;
    }

    @Override
    public SalesTrendDTO getSalesTrend(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            throw new BadRequestException("Both 'from' and 'to' dates are required.");
        }
        if (from.isAfter(to)) {
            throw new BadRequestException("'from' date must not be after 'to' date.");
        }
        LocalDateTime fromDT = from.atStartOfDay();
        LocalDateTime toDT   = to.atTime(23, 59, 59);
        SalesTrendDTO result = salesRepository.getSalesTrend(fromDT, toDT);
        if (result == null || result.getTotalSalesCount() == 0) {
            throw new ResourceNotFoundException("No sales found between " + from + " and " + to + ".");
        }
        return result;
    }

    @Override
    public List<AuthorRoyaltyDTO> getAuthorRoyalties() {
        List<AuthorRoyaltyDTO> result = titleAuthorRepository.getAuthorRoyalties();
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("No royalty data found.");
        }
        return result;
    }

    @Override
    public List<TopAuthorDTO> getTopAuthorsBySales() {
        List<TopAuthorDTO> result = titleAuthorRepository.getTopAuthorsBySales();
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("No author sales data found.");
        }
        return result;
    }
}
