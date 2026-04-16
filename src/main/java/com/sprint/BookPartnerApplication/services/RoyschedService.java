package com.sprint.BookPartnerApplication.services;

import com.sprint.BookPartnerApplication.entity.Roysched;

import java.util.List;

public interface RoyschedService {

    Roysched createRoysched(Roysched roysched);

    List<Roysched> getRoyschedByTitle(String titleId);

    Roysched updateRoysched(Integer royaltyId, Roysched roysched);
}