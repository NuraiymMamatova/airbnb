package com.example.airbnbb7.converter.user;

import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.dto.request.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserConverterRequest {

    public User create(UserRequest userRequest) {
        if (userRequest == null) return null;
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setImage(userRequest.getImage());

        return user;
    }


    public void update(User user, UserRequest userRequest) {
        if (userRequest.getName() != null) {
            user.setName(userRequest.getName());
        }
        if (userRequest.getPassword() != null) {
            user.setPassword(userRequest.getPassword());
        }
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail());
        }
        if (userRequest.getImage() != null) {
            user.setImage(userRequest.getImage());
        }

    }
}
