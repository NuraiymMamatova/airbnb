package com.example.airbnbb7.db.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Table(name = "feedbacks")
@NoArgsConstructor
public class Feedback {

    @Id
    @SequenceGenerator(name = "feedback_gen", sequenceName = "feedback_seq", allocationSize = 1, initialValue = 6)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_gen")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "rating")
    private int rating;

    @Column(name = "created_feedback")
    private LocalDate createdFeedback;

    @ElementCollection(fetch = LAZY)
    private List<String> image;

    @Column(name = "likes")
    private Long like;

    @Column(name = "dislike")
    private Long dislike;

    @ElementCollection
    private Map<Long, Boolean> likes;

    @ElementCollection
    private Map<Long, Boolean> dislikes;

    @ManyToOne(cascade = {REFRESH, DETACH, MERGE})
    private User user;

    @ManyToOne(cascade = {REFRESH, DETACH, MERGE})
    private House house;

    public Feedback(String text, int rating, LocalDate createdFeedback, List<String> image, Long like, Long dislike, User user, House house) {
        this.text = text;
        this.rating = rating;
        this.createdFeedback = createdFeedback;
        this.image = image;
        this.like = like;
        this.dislike = dislike;
        this.user = user;
        this.house = house;
    }

    public Feedback(String text, int rating, List<String> image, User user, House house) {
        this.text = text;
        this.rating = rating;
        this.image = image;
        this.user = user;
        this.house = house;
    }
}
