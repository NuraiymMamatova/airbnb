package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.Feedback;
import com.example.airbnbb7.dto.response.FeedbackResponse;
import com.example.airbnbb7.dto.response.UserResponseForFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("select f from Feedback f where f.house.id = :houseId")
    List<Feedback> getAllFeedbackByHouseId(Long houseId);

    @Query("select new com.example.airbnbb7.dto.response.UserResponseForFeedback(f.user.id, f.user.name, f.user.image) from Feedback f where f.id = :feedbackId")
    UserResponseForFeedback findOwnerFeedbackByFeedbackId(Long feedbackId);

    @Query("select new com.example.airbnbb7.dto.response.FeedbackResponse(f.id, f.text, f.rating, f.createdFeedback, f.like, f.dislike) from Feedback f where f.id = :feedbackId")
    FeedbackResponse findFeedbackByFeedbackId(Long feedbackId);

}