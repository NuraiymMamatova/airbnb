package com.example.airbnbb7.api;

import com.example.airbnbb7.db.service.UserService;
import com.example.airbnbb7.db.service.serviceImpl.UserServiceImpl;
import com.example.airbnbb7.dto.request.UserRequest;
import com.example.airbnbb7.dto.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/auth")
@Tag(name = "Auth API", description = "Authentication API")
public class AuthApi {

    private final UserService userService;

    private final UserServiceImpl authService;

    @PostMapping("/login")
    @Operation(summary = "Sign in", description = "Any user can authenticate")
    public LoginResponse login(@RequestBody UserRequest request) {
        return userService.login(request);
    }

    @PostMapping("/google")
    @Operation(summary = "Sign up & sign in", description = "Authenticate via Google")
    public LoginResponse authResponse(@RequestParam String tokenId) {
        return authService.authWithGoogle(tokenId);
    }
}
