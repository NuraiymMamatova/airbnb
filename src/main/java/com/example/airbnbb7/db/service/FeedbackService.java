package com.example.airbnbb7.db.service;

import com.example.airbnbb7.dto.response.FeedbackResponse;

import java.util.List;

public interface FeedbackService {

    List<FeedbackResponse> getFeedbacksByHouseId(Long houseId);
}
