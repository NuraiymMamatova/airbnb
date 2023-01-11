package com.example.airbnbb7.converter.user;

import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.dto.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverterResponse {

    public UserResponse create(User user) {
        if (user == null) return null;
        UserResponse userResponse = new UserResponse();
        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setImage(user.getImage());
        return userResponse;
    }

    public List<UserResponse> getAll(List<User> users) {
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            userResponses.add(create(user));
        }
        return userResponses;
    }
}
