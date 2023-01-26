package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.repository.FeedbackRepository;
import com.example.airbnbb7.db.service.FeedbackService;
import com.example.airbnbb7.dto.response.FeedbackResponse;
import com.example.airbnbb7.dto.response.UserResponseForFeedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Override
    public List<FeedbackResponse> getFeedbacksByHouseId(Long houseId) {
        List<FeedbackResponse> feedbackResponses = new ArrayList<>();
        for (FeedbackResponse feedback : feedbackRepository.getFeedbacksByHouseId(houseId)) {
            List<UserResponseForFeedback> allOwnerByFeedbackId = feedbackRepository.findAllOwnerByFeedbackId(feedback.getId());
            feedback.setImage(feedbackRepository.findImagesByFeedbackId(feedback.getId()));
            for (UserResponseForFeedback user : allOwnerByFeedbackId) {
                feedback.setOwner(user);
                feedbackResponses.add(feedback);
            }
        }
        return feedbackResponses;
    }

}
