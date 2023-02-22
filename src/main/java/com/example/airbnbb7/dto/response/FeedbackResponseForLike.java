package com.example.airbnbb7.dto.response;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FeedbackResponseForLike {

    private Long id;

    private Long like;

    private Boolean liked = false;

    private Long dislike;

    private Boolean disliked = false;

    public FeedbackResponseForLike(Long id, Long like, Long dislike) {
        this.id = id;
        this.like = like;
        this.dislike = dislike;
    }
}