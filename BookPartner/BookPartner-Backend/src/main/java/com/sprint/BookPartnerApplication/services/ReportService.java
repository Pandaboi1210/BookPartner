package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.response.report.AuthorRoyaltyDTO;
import com.sprint.BookPartnerApplication.dto.response.report.SalesByPublisherDTO;
import com.sprint.BookPartnerApplication.dto.response.report.SalesByTitleDTO;
import com.sprint.BookPartnerApplication.dto.response.report.SalesTrendDTO;
import com.sprint.BookPartnerApplication.dto.response.report.TopAuthorDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    List<SalesByTitleDTO> getSalesByTitle();

    List<SalesByPublisherDTO> getSalesByPublisher();

    SalesTrendDTO getSalesTrend(LocalDate from, LocalDate to);

    List<AuthorRoyaltyDTO> getAuthorRoyalties();

    List<TopAuthorDTO> getTopAuthorsBySales();
}
