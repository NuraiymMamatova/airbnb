package com.example.airbnbb7.api;

import com.example.airbnbb7.db.service.UserService;
import com.example.airbnbb7.db.service.serviceImpl.UserServiceImpl;
import com.example.airbnbb7.dto.request.UserRequest;
import com.example.airbnbb7.dto.response.LoginResponse;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
@Tag(name = "Auth API", description = "Authentication API")
public class AuthApi {

    private final UserService userService;

    private final UserServiceImpl authService;

    @PostMapping("login")
    @Operation(summary = "Sign in", description = "Authentication")
    public LoginResponse login(@RequestBody UserRequest request) {
        return userService.login(request);
    }

    @PostMapping("/google")
    @Operation(summary = "Sign up & sign in", description = "via google")
    public LoginResponse authResponse(String tokenId) throws FirebaseAuthException {
        return authService.authWithGoogle(tokenId);
    }
}
