package com.example.airbnbb7.authwithgoogle.service;

import com.example.airbnbb7.authwithgoogle.dto.response.AuthResponse;
import com.example.airbnbb7.authwithgoogle.entity.AuthInfo;
import com.example.airbnbb7.authwithgoogle.repository.AuthInfoRepository;
import com.example.airbnbb7.authwithgoogle.repository.RoleRepository;
import com.example.airbnbb7.authwithgoogle.repository.UserRepository;
import com.example.airbnbb7.authwithgoogle.security.jwt.JwtUtils;
import com.example.airbnbb7.db.entities.User;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthInfoRepository authInfoRepository;

    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @PostConstruct
    void init() throws IOException {
        GoogleCredentials googleCredentials =
                GoogleCredentials.fromStream(new ClassPathResource("airbnb-b7.json").getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();
        FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
    }

    public AuthResponse authWithGoogle(String tokenId) throws FirebaseAuthException {
        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenId);

        User user = null;
        if (authInfoRepository.findByEmail(firebaseToken.getEmail()) == null) {
            user = new User();
            user.setAuthInfo(new AuthInfo(firebaseToken.getEmail(),
                    firebaseToken.getEmail(),
                    firebaseToken.getName(),
                    roleRepository.findByName("USER_VENDOR")));
            user.setName(firebaseToken.getName());
            user.setEmail(firebaseToken.getEmail());
            user = userRepository.save(user);
        }

        user = userRepository.findByEmail(firebaseToken.getEmail());

        String token = jwtUtils.generateToken(user.getAuthInfo().getEmail());
        return new AuthResponse(user.getAuthInfo().getEmail(), token, authInfoRepository.findRoleByUser(user));
    }

}
