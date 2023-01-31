package com.example.airbnbb7.db.service;

import com.example.airbnbb7.dto.response.HouseResponse;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HouseService {

    List<HouseResponse> globalSearch(String searchEngine);
}
