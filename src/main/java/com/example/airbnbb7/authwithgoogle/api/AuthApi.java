package com.example.airbnbb7.authwithgoogle.api;

import com.example.airbnbb7.authwithgoogle.dto.response.AuthResponse;
import com.example.airbnbb7.authwithgoogle.service.AuthService;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApi {

    private final AuthService authService;

    @PostMapping("/google")
    public AuthResponse authResponse(String tokenId) throws FirebaseAuthException {
        return authService.authWithGoogle(tokenId);
    }

}
