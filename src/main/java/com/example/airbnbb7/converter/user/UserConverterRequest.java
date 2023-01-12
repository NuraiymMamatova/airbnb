package com.example.airbnbb7.converter.user;

import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.dto.request.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserConverterRequest {

    public User create(UserRequest userRequest) {

        if (userRequest == null) return null;
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        return user;
    }

    public void update(User user, UserRequest userRequest) {

        if (userRequest.getPassword() != null) {
            user.setPassword(userRequest.getPassword());
        }
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail());
        }
    }
}
