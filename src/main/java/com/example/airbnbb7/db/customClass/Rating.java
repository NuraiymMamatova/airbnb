package com.example.airbnbb7.db.customClass;

import com.example.airbnbb7.db.entities.Feedback;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
public class Rating {

    private double sumOfRating;

    private int one;

    private int two;

    private int three;

    private int four;

    private int five;

    public double getRating(List<Feedback> feedbacks) {
        if (feedbacks == null) return 0;
        List<Integer> ratings = new ArrayList<>();
            for (Feedback feedback : feedbacks) {
                ratings.add(feedback.getRating());
            }
            double sum = 0;
            for (Integer rating : ratings) {
                sum += rating;
            }
            if (ratings.size() != 0) {
                sum = sum / ratings.size();
            }
            DecimalFormat df = new DecimalFormat("#.#");
            return Double.parseDouble(df.format(sum));
    }

    public Rating getRatingCount(List<Feedback> feedbacks) {
        Rating rating = new Rating();
        if (feedbacks == null) return rating;
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
        if (countOfPeople != 0) {
            rating.setFive((rating.getFive() * 100) / countOfPeople);
            rating.setFour((rating.getFour() * 100) / countOfPeople);
            rating.setThree((rating.getThree() * 100) / countOfPeople);
            rating.setTwo((rating.getTwo() * 100) / countOfPeople);
            rating.setOne((rating.getOne() * 100) / countOfPeople);
        }
        rating.setSumOfRating(getRating(feedbacks));
        return rating;
    }
}
