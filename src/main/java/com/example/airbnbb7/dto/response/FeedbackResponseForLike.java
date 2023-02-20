package com.example.airbnbb7.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeedbackResponseForLike {

    private Long id;

    private Long like;

    private Boolean liked = false;

    private Long dislike;

    private Boolean disliked = false;
}