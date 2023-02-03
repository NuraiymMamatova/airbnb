package com.example.airbnbb7.db.customclass;

import com.example.airbnbb7.db.entities.Feedback;
import com.example.airbnbb7.db.repository.FeedbackRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Rating {

    private double sumOfRating;

    private int one;

    private int two;

    private int three;

    private int four;

    private int five;

    private final FeedbackRepository feedbackRepository;

    public double getRating(Long houseId) {
        List<Feedback> feedbacks = feedbackRepository.getAllFeedbackByHouseId(houseId);
        List<Integer> ratings = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            ratings.add(feedback.getRating());
        }
        double sum = 0;
        for (Integer rating : ratings) {
            sum += rating;
        }
        sum = sum / ratings.size();
        String.format("%.1f", sum);
        return sum;
    }

    public Rating getRatingCount(Long houseId) {
        List<Feedback> feedbacks = feedbackRepository.getAllFeedbackByHouseId(houseId);
        Rating rating = new Rating(getFeedbackRepository());
        int countOfPeople = 0;
        for (Feedback feedback : feedbacks) {
            switch (feedback.getRating()) {
                case 1 -> {
                    rating.setOne(rating.getOne() + 1);
                    countOfPeople++;
                }
                case 2 -> {
                    rating.setTwo(rating.getTwo() + 1);
                    countOfPeople++;
                }
                case 3 -> {
                    rating.setThree(rating.getThree() + 1);
                    countOfPeople++;
                }
                case 4 -> {
                    rating.setFour(rating.getFour() + 1);
                    countOfPeople++;
                }
                case 5 -> {
                    rating.setFive(rating.getFive() + 1);
                    countOfPeople++;
                }
            }
        }
        rating.setFive((rating.getFive() * 100) / countOfPeople);
        rating.setFour((rating.getFour() * 100) / countOfPeople);
        rating.setThree((rating.getThree() * 100) / countOfPeople);
        rating.setTwo((rating.getTwo() * 100) / countOfPeople);
        rating.setOne((rating.getOne() * 100) / countOfPeople);
        rating.setSumOfRating(getRating(houseId));
        return rating;
    }

}
