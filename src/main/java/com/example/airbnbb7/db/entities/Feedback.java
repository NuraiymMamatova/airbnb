package com.example.airbnbb7.db.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;


@Entity
@Getter
@Setter
@Table(name = "feedbacks")
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

    @Column(name = "take_a_look")
    private Long like;

    @Column(name = "dislike")
    private Long dislike;

    @ElementCollection(fetch = LAZY)
    private List<Integer> ratings = new ArrayList<>();

    @ManyToOne(cascade = {REFRESH, DETACH, MERGE})
    private User user;

    @ManyToOne(cascade = {REFRESH, DETACH, MERGE})
    private House house;

}
