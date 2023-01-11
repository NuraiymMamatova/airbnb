package com.example.airbnbb7.service;

import com.example.airbnbb7.dto.request.UserRequest;
import com.example.airbnbb7.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;

public interface UserService extends UserDetailsService {

    UserResponse saveUser(UserRequest userRequest) throws IOException;

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
