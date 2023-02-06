package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.customClass.SimpleResponse;
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
    public SimpleResponse saveFeedback(FeedbackRequest feedbackRequest, Long houseId) {
        User user = userRepository.findById(UserRepository.getUserId()).orElseThrow(() -> new NotFoundException("User not found!"));
        House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
        Feedback feedback = new Feedback(feedbackRequest.getText(), feedbackRequest.getRating(),
                feedbackRequest.getCreatedFeedback(), feedbackRequest.getImage(),
                feedbackRequest.getLike(), feedbackRequest.getDislike(), user, house);
        feedbackRepository.save(feedback);
        return new SimpleResponse("Feedback successfully saved!");
    }

    @Override
    public SimpleResponse deleteFeedback(Long feedbackId) {
        feedbackRepository.delete(feedbackRepository.findById(feedbackId).orElseThrow(() -> new NotFoundException("Feedback not found!")));
        return new SimpleResponse("Feedback successfully deleted!");
    }

    @Override
    public SimpleResponse updateFeedback(Long feedbackId, FeedbackRequest feedbackRequest) {
        Feedback feedback = feedbackRepository.findById(feedbackId).orElseThrow(() -> new NotFoundException("Feedback not found!"));
        if (feedbackRequest.getText() != null) {
            feedback.setText(feedbackRequest.getText());
        }
        if (feedbackRequest.getRating() != 0) {
            feedback.setRating(feedbackRequest.getRating());
        }
        if (feedbackRequest.getImage() != null) {
            feedback.setImage(feedbackRequest.getImage());
        }
        if (feedbackRequest.getLike() != null) {
            feedback.setLike(feedbackRequest.getLike());
        }
        if (feedbackRequest.getDislike() != null) {
            feedback.setDislike(feedbackRequest.getDislike());
        }
        feedbackRepository.save(feedback);
        return new SimpleResponse("Feedback successfully updated!");
    }
}
