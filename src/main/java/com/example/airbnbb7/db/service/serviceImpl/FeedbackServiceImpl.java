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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
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
                log.info("Feedback by user {} successfully saved!", feedback.getUser());
                return new SimpleResponse("Feedback successfully saved!");
            }
            log.warn("You can't leave feedback for your house by id {} ", house.getId());
            return new SimpleResponse("You can't leave feedback for your house!");
        }
        log.warn("Authentication {} can not be null!", authentication.getPrincipal());
        throw new BadRequestException("Authentication can not be null!");
    }

    @Override
    public SimpleResponse deleteFeedback(Authentication authentication, Long feedbackId) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            Feedback feedback = feedbackRepository.findById(feedbackId).orElseThrow(() -> new NotFoundException("Feedback not found!"));
            if (feedback.getUser().getId() == user.getId()) {
                feedbackRepository.delete(feedbackRepository.findById(feedbackId).orElseThrow(() -> new NotFoundException("Feedback not found!")));
                log.info("feedback by user {} successfully deleted ", feedback.getUser());
                return new SimpleResponse("Feedback successfully deleted!");
            }
            log.warn("You can't delete other people's comments");
            throw new BadRequestException("You can't delete other people's comments!");
        }
        log.warn("delete forbidden");
        throw new BadCredentialsException("Forbidden!");
    }

    @Override
    public SimpleResponse updateFeedback(Authentication authentication, Long feedbackId, FeedbackRequestForUpdate feedbackRequest, boolean like, boolean dislike) {
        Feedback feedback = feedbackRepository.findById(feedbackId).orElseThrow(() -> new NotFoundException("Feedback not found!"));
        feedbackRequest = new FeedbackRequestForUpdate(feedback.getText(), feedback.getRating(), LocalDate.now(), feedback.getImage());
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
            if (like) {
                liking(feedbackId, authentication);
            }
            if (dislike) {
                disLiking(feedbackId, authentication);
            }
            feedbackRepository.save(feedback);
            log.info("Feedback by user {} successfully updated", feedback.getUser());
            return new SimpleResponse("Feedback successfully updated!");
        }
        log.warn("Authentication cannot be null");
        throw new BadRequestException("Authentication cannot be null!");
    }

    private void liking(Long feedbackId, Authentication authentication) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            Feedback feedback = feedbackRepository.findById(feedbackId).orElseThrow(() -> new NotFoundException("not found!"));
            if (feedback.getLikes().containsKey(user.getId())) {
                feedback.setLike(feedback.getLike() - 1);
                feedback.getLikes().remove(user.getId());
                feedbackRepository.save(feedback);
            } else {
                feedback.getLikes().put(user.getId(), true);
                feedback.setLike(feedback.getLike() + 1);
                feedbackRepository.save(feedback);
            }
            log.info("successfully liked");
        }
    }

    private void disLiking(Long feedbackId, Authentication authentication) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            Feedback feedback = feedbackRepository.findById(feedbackId).orElseThrow(() -> new NotFoundException("not found!"));
            if (feedback.getDislikes().containsKey(user.getId())) {
                feedback.setDislike(feedback.getDislike() - 1);
                feedback.getDislikes().remove(user.getId());
                feedbackRepository.save(feedback);
            } else {
                feedback.getDislikes().put(user.getId(), true);
                feedback.setDislike(feedback.getDislike() + 1);
                feedbackRepository.save(feedback);
            }
            log.info("successfully disliked");
        }
    }

}