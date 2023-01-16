package com.example.airbnbb7.api;

import com.example.airbnbb7.dto.request.UserRequest;
import com.example.airbnbb7.dto.response.LoginResponse;
import com.example.airbnbb7.db.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
@Tag(name = "Auth Api",description = "Authentication Api")
public class AuthApi {

    private final UserService userService;

    @PostMapping("login")
    @Operation(summary = "Sign in",description = "Only registered users can login")
    public LoginResponse login(@RequestBody UserRequest request) {
        return userService.login(request);
    }
}
