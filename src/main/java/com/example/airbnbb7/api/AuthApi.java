package com.example.airbnbb7.api;

import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.db.service.UserService;
import com.example.airbnbb7.db.service.serviceImpl.UserServiceImpl;
import com.example.airbnbb7.dto.request.UserRequest;
import com.example.airbnbb7.dto.response.HouseResponse;
import com.example.airbnbb7.dto.response.LoginResponse;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth API", description = "Authentication API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthApi {

    private final UserService userService;

    private final UserServiceImpl authService;

    private final HouseService houseService;

    @PostMapping("/login")
    @Operation(summary = "Sign in", description = "Any user can authenticate")
    public LoginResponse login(@RequestBody UserRequest request) {
        return userService.login(request);
    }

    @PostMapping("/google")
    @Operation(summary = "Sign up & sign in", description = "Authenticate via Google")
    public LoginResponse authResponse(@RequestParam String tokenId) throws FirebaseAuthException {
        return authService.authWithGoogle(tokenId);
    }

    @GetMapping("/{searchEngine}")
    public List<HouseResponse> globalSearch(@PathVariable String searchEngine){
        return houseService.globalSearch(searchEngine);
    }
}
