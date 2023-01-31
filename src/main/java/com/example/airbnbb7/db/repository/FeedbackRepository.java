package com.example.airbnbb7.db.repository;

import com.example.airbnbb7.db.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query("select f from Feedback f where f.house.id = :houseId")
    List<Feedback> getAllFeedbackByHouseId(Long houseId);
}