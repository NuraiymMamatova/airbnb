package com.example.airbnbb7.db.service.serviceImpl;

import com.example.airbnbb7.db.entities.Feedback;
import com.example.airbnbb7.db.repository.FeedbackRepository;
import com.example.airbnbb7.db.repository.HouseRepository;
import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.dto.response.AccommodationResponse;
import com.example.airbnbb7.dto.response.HouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl {

    private final HouseRepository houseRepository;
    private final FeedbackRepository feedbackRepository;

    public List<HouseResponse> getPopularHouses(Pageable pageable) {
        List<HouseResponse> houseResponses = houseRepository.getPopularHouse(pageable);
        List<HouseResponse> h = new ArrayList<>();
        double rating = 0;
        Long booked = 0l;
        for (HouseResponse houseResponse:houseResponses) {
            double r = getRating(houseResponse.getId());
            if (r>rating && houseResponse.getCountOfBookedUser() > booked) {
                rating = houseResponse.getRating();
                booked = houseResponse.getCountOfBookedUser();
            }
            h.add(houseResponse);
            houseResponses.remove(houseResponse);
        }
        return h;
    }
    public List<HouseResponse> getAllPopularHouse(Pageable pageable){
        return houseRepository.getPopularHouse(pageable);
    }
    public List<AccommodationResponse> getAllPopularApartments(Pageable pageable){
        return houseRepository.getPopularApartment(pageable);
    }

    public List<AccommodationResponse> getAllLatestHouses(Pageable pageable){
        return houseRepository.getLatestAccommodation(pageable);
    }

    public double getRating(Long houseId) {
        List<Feedback> feedbacks = feedbackRepository.getAllFeedbackByHouseId(houseId);
        List<Integer> ratings = new ArrayList<>();
        for (Feedback feedback :  feedbacks) {
            ratings.add(feedback.getRating());
        }
        double sum = 0;
        for (Integer rating : ratings) {
            sum +=rating;
        }
        sum = sum / ratings.size();
        String.format("%.1f", sum);
        return sum;
    }
}
