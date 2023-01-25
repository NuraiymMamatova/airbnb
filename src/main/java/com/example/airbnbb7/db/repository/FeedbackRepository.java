package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.Feedback;
import com.example.airbnbb7.dto.response.FeedbackResponse;
import com.example.airbnbb7.dto.response.user.UserResponse;
import com.example.airbnbb7.dto.response.user.UserResponseForFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("select new com.example.airbnbb7.dto.response.FeedbackResponse(f.id, f.text, f.rating, f.createdFeedback, f.like, f.dislike) from Feedback f where f.house.id = :houseId")
    List<FeedbackResponse> getFeedbacksByHouseId(Long houseId);

    @Query("select new com.example.airbnbb7.dto.response.user.UserResponseForFeedback(u.id, u.name, u.image) from User u where u.id = (select f.user.id from Feedback f where f.id = :feedbackId)")
    List<UserResponseForFeedback> findAllOwnerByFeedbackId(Long feedbackId);

    @Query(value = "select image from feedback_image where feedback_id = :feedbackId", nativeQuery = true)
    List<String> findImagesByFeedbackId(Long feedbackId);

}