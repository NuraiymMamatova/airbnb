package com.example.airbnbb7.dto.request;

import com.example.airbnbb7.db.entities.House;
import com.example.airbnbb7.db.entities.User;
import lombok.*;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeedbackRequest {

    private String text;

    private int rating;

    private LocalDate createdFeedback;

    private List<String> image;

    private Long like;

    private Long dislike;

    private User user;

    private House house;
}
