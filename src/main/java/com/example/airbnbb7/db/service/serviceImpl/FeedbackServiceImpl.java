package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.entities.Feedback;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.repository.FeedbackRepository;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.repository.UserRepository;
import com.example.airbnbb7.db.service.FeedbackService;
import com.example.airbnbb7.dto.request.FeedbackRequest;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;

    @Override
    public void saveFeedback(FeedbackRequest feedbackRequest, Long houseId) {
        User user = userRepository.findById(UserRepository.getUserId()).orElseThrow(() -> new NotFoundException("User not found!"));
        House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        Feedback feedback = new Feedback(feedbackRequest.getText(), feedbackRequest.getRating(),
                feedbackRequest.getCreatedFeedback(), feedbackRequest.getImage(),
                feedbackRequest.getLike(), feedbackRequest.getDislike(), user, house);
        feedbackRepository.save(feedback);
    }

    @Override
    public void deleteFeedback(Long feedbackId) {
        feedbackRepository.delete(feedbackRepository.findById(feedbackId).orElseThrow(() -> new NotFoundException("Feedback not found!")));
    }

    @Override
    public void updateFeedback(Long feedbackId, FeedbackRequest feedbackRequest) {
        Feedback feedback = feedbackRepository.findById(feedbackId).orElseThrow(() -> new NotFoundException("Feedback not found!"));
        feedback.setText(feedbackRequest.getText());
        feedback.setRating(feedbackRequest.getRating());
        feedback.setCreatedFeedback(feedbackRequest.getCreatedFeedback());
        feedback.setImage(feedbackRequest.getImage());
        feedback.setLike(feedbackRequest.getLike());
        feedback.setDislike(feedbackRequest.getDislike());
        feedbackRepository.save(feedback);
    }
}
