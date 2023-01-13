package com.example.airbnbb7.api;

import com.example.airbnbb7.dto.request.UserRequest;
import com.example.airbnbb7.dto.response.LoginResponse;
import com.example.airbnbb7.db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
public class AuthApi {

    private final UserService userService;

    @PostMapping("login")
    public LoginResponse login(@RequestBody UserRequest request) {
        return userService.login(request);
    }
}
