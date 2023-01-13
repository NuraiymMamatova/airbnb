package com.example.airbnbb7.db.service;

import com.example.airbnbb7.dto.request.UserRequest;
import com.example.airbnbb7.dto.response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UserService extends UserDetailsService {

    ResponseEntity<LoginResponse> getLogin(UserRequest request);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
