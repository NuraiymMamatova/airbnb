package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.dto.request.FeedbackRequest;
import org.springframework.stereotype.Service;

@Service
public interface FeedbackService {

    SimpleResponse saveFeedback(FeedbackRequest feedbackRequest, Long houseId);

    SimpleResponse deleteFeedback(Long feedbackId);

    SimpleResponse updateFeedback(Long feedbackId, FeedbackRequest feedbackRequest);
}
