package com.example.airbnbb7.db.service;

import com.example.airbnbb7.dto.request.UserRequest;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.dto.response.LoginResponse;
import com.example.airbnbb7.dto.response.ProfileResponse;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {

    LoginResponse login(UserRequest request);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    LoginResponse authWithGoogle(String tokenId) throws FirebaseAuthException;

    String getEmail();

    ProfileResponse userProfile(String mainInUserProfile, String houseSorting, String sortingHousesByValue, String sortingHousesByRating, Long userId, int paginationSize);

}
