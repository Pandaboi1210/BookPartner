package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.dto.request.RoyschedRequestDTO;
import com.sprint.BookPartnerApplication.dto.response.RoyschedResponseDTO;

import java.util.List;

public interface RoyschedService {

    RoyschedResponseDTO createRoysched(RoyschedRequestDTO requestDTO);

    List<RoyschedResponseDTO> getRoyschedByTitle(String titleId);

    RoyschedResponseDTO updateRoysched(Integer royaltyId, RoyschedRequestDTO requestDTO);
}