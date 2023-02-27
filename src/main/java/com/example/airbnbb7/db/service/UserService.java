package com.example.airbnbb7.db.service;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.dto.request.UserRequest;
import com.example.airbnbb7.dto.response.LoginResponse;
import com.example.airbnbb7.dto.response.ProfileAdminResponse;
import com.example.airbnbb7.dto.response.ProfileResponse;
import com.example.airbnbb7.dto.response.UserAdminResponse;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    LoginResponse login(UserRequest request);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    LoginResponse authWithGoogle(String tokenId) throws FirebaseAuthException;

    ProfileResponse userProfile(String mainInUserProfile, String sortHousesAsDesired, String sortHousesByApartments, String sortHousesByHouses, String sortingHousesByValue, String sortingHousesByRating, Authentication authentication, int page, int size);

    List<UserAdminResponse> getAllUsers();

    List<UserAdminResponse> deleteUser(Long userId);

    AnnouncementService getUserByIdBookingOrAnnouncement(Long userId, String bookingOrAnnouncement);

    void allBlocked(Long userId);
}
