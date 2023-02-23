package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.dto.request.FeedbackRequestForSave;
import com.example.airbnbb7.dto.request.FeedbackRequestForUpdate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface FeedbackService {

    SimpleResponse saveFeedback(FeedbackRequestForSave feedbackRequestForSave, Long houseId, Authentication authentication);

    SimpleResponse deleteFeedback(Authentication authentication, Long feedbackId);

    SimpleResponse updateFeedback(Authentication authentication, Long feedbackId, FeedbackRequestForUpdate feedbackRequest);

    void liking(Long feedbackId, User user);

    void disLiking(Long feedbackId,User user);
}
