package com.example.airbnbb7.db.service;

import com.example.airbnbb7.dto.request.FeedbackRequest;
import org.springframework.stereotype.Service;

@Service
public interface FeedbackService {

    void saveFeedback(FeedbackRequest feedbackRequest, Long houseId);

    void deleteFeedback(Long feedbackId);

    void updateFeedback(Long feedbackId, FeedbackRequest feedbackRequest);
}
