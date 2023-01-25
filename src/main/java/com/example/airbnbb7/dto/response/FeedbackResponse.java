package com.example.airbnbb7.dto.response;

import com.example.airbnbb7.dto.response.user.UserResponse;
import com.example.airbnbb7.dto.response.user.UserResponseForFeedback;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {

    private Long id;

    private String text;

    private int rating;

    private LocalDate createdFeedback;

    private List<String> image;

    private Long like;

    private Long dislike;

    private UserResponseForFeedback owner;

    public FeedbackResponse(Long id, String text, int rating, LocalDate createdFeedback, Long like, Long dislike) {
        this.id = id;
        this.text = text;
        this.rating = rating;
        this.createdFeedback = createdFeedback;
        this.like = like;
        this.dislike = dislike;
    }
}
