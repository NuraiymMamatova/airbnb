package com.example.airbnbb7.db.service;

import com.example.airbnbb7.dto.request.UserRequest;
import com.example.airbnbb7.dto.response.LoginResponse;
import com.example.airbnbb7.dto.response.ProfileResponse;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {

    LoginResponse login(UserRequest request);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    LoginResponse authWithGoogle(String tokenId) throws FirebaseAuthException;

    ProfileResponse userProfile(String mainInUserProfile, String sortHousesAsDesired, String sortHousesByApartments, String sortHousesByHouses, String sortingHousesByValue, String sortingHousesByRating, Long userId, int page, int size);

}
