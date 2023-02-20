package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.entities.Feedback;
import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.repository.FeedbackRepository;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.service.FeedbackService;
import com.example.airbnbb7.dto.request.FeedbackRequestForSave;
import com.example.airbnbb7.dto.request.FeedbackRequestForUpdate;
import com.example.airbnbb7.exceptions.BadCredentialsException;
import com.example.airbnbb7.exceptions.BadRequestException;
import com.example.airbnbb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    private final HouseRepository houseRepository;

    @Override
    public SimpleResponse saveFeedback(FeedbackRequestForSave feedbackRequestForSave, Long houseId, Authentication authentication) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundException("House not found!"));
            if (user.getId() != house.getOwner().getId()) {
                Feedback feedback = new Feedback(feedbackRequestForSave.getText(), feedbackRequestForSave.getRating(), LocalDate.now(), feedbackRequestForSave.getImage(), 0L, 0L, user, house);
                feedbackRepository.save(feedback);
                return new SimpleResponse("Feedback successfully saved!");
            }
            return new SimpleResponse("You can't leave feedback for your house!");
        }
        throw new BadRequestException("Authentication can not be null!");
    }

    @Override
    public SimpleResponse deleteFeedback(Authentication authentication, Long feedbackId) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            Feedback feedback = feedbackRepository.findById(feedbackId).orElseThrow(() -> new NotFoundException("Feedback not found!"));
            if (feedback.getUser().getId() == user.getId()) {
                feedbackRepository.delete(feedbackRepository.findById(feedbackId).orElseThrow(() -> new NotFoundException("Feedback not found!")));
                return new SimpleResponse("Feedback successfully deleted!");
            }
            throw new BadRequestException("You can't delete other people's comments!");
        }
        throw new BadCredentialsException("Forbidden!");
    }

    @Override
    public SimpleResponse updateFeedback(Authentication authentication, Long feedbackId, FeedbackRequestForUpdate feedbackRequest) {
        Feedback feedback = feedbackRepository.findById(feedbackId).orElseThrow(() -> new NotFoundException("Feedback not found!"));
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            if (user == feedback.getUser()) {
                if (feedbackRequest.getText() != null) {
                    feedback.setText(feedbackRequest.getText());
                }
                if (feedbackRequest.getRating() != 0) {
                    feedback.setRating(feedbackRequest.getRating());
                }
                if (feedbackRequest.getImage() != null) {
                    feedback.setImage(feedbackRequest.getImage());
                }
            }
            Map<Long, Boolean> like = feedback.getLikes();
            Map<Long, Boolean> dislike = feedback.getDislikes();
            if (like == null) {
                like = new HashMap<>();
            }
            if (like.containsKey(feedbackId)) {
                feedback.setLike(feedback.getLike() - 1);
                like.remove(feedbackId);
            }
            if (feedbackRequest.getLike() && !feedbackRequest.getDislike()) {
                {
                    like.put(feedbackId, feedbackRequest.getLike());
                    feedback.setLike(feedback.getLike() + 1);
                }
            }
            if (dislike == null) {
                dislike = new HashMap<>();
            }
            if (dislike.containsKey(feedbackId)) {
                feedback.setDislike(feedback.getDislike() - 1);
                dislike.remove(feedbackId);
            }
            if (feedbackRequest.getDislike() && !feedbackRequest.getLike()) {
                dislike.put(feedbackId, feedbackRequest.getDislike());
                feedback.setDislike(feedback.getDislike() + 1);
            }
            feedbackRepository.save(feedback);
            return new SimpleResponse("Feedback successfully updated!");
        }
        throw new BadRequestException("Authentication cannot be null!");
    }
}
