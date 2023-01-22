package com.example.airbnbb7.api;

import com.example.airbnbb7.db.service.UserService;
import com.example.airbnbb7.db.service.serviceImpl.UserServiceImpl;
import com.example.airbnbb7.dto.request.UserRequest;
import com.example.airbnbb7.dto.response.LoginResponse;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
@CrossOrigin(origins = "*",maxAge = 3600)
public class AuthApi {

    private final UserService userService;

    private final UserServiceImpl authService;

    @PostMapping("login")
    public LoginResponse login(@RequestBody UserRequest request) {
        return userService.login(request);
    }

    @PostMapping("/google")
    public LoginResponse authResponse(String tokenId) throws FirebaseAuthException {
        return authService.authWithGoogle(tokenId);
    }
}
