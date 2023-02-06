package com.example.airbnbb7.db.service;

import com.example.airbnbb7.dto.request.UserRequest;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.dto.response.LoginResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {

    LoginResponse login(UserRequest request);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    String getEmail();

    List<HouseResponse> userProfile(String mainInUserProfile, String houseSorting, String sortingHousesByValue, String sortingHousesByRating);

}
