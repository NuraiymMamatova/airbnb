package com.example.airbnbb7.db.service;

import com.example.airbnbb7.dto.response.AccommodationResponse;
import org.springframework.stereotype.Service;

@Service
public interface HouseService {
    AccommodationResponse getLatestAccommodation ();
}
