package com.example.airbnbb7.dto.response;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {

    private Long id;

    private String text;

    private int rating;

    private LocalDate createdFeedback;

    private List<String> image;

    private Long like;

    private Long dislike;

    private User user;

    private House house;

}
