package com.example.airbnbb7.converter.login;

import com.example.airbnbb7.db.entities.Role;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.dto.response.LoginResponse;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class LoginConverter {

    public LoginResponse loginView(String token,
                                   String message,
                                   User user) {
        var loginResponse = new LoginResponse();
        if (user != null) {
            setAuthorite(loginResponse, user.getRoles());
        }
        loginResponse.setJwtToken(token);
        loginResponse.setMessage(message);
        return loginResponse;
    }

    public LoginResponse loginNotView(String message) {
        var  loginResponse= new LoginResponse();
        loginResponse.setMessage(message);
        return loginResponse;
    }

    private void setAuthorite(LoginResponse loginResponse, List<Role> roles) {
        Set<String> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.add(role.getNameOfRole());
        }
        loginResponse.setAuthorities(authorities);
    }
}
