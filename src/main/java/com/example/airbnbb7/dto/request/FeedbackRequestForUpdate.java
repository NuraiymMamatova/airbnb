package com.example.airbnbb7.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequestForUpdate {

    private String text;

    private int rating;

    private LocalDate createdFeedback;

    private List<String> image;

    private Long like;

    private Long dislike;

    public FeedbackRequestForUpdate(String text, int rating, LocalDate createdFeedback, List<String> image) {
        this.text = text;
        this.rating = rating;
        this.createdFeedback = createdFeedback;
        this.image = image;
    }
}