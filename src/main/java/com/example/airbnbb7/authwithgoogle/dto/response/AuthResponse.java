package com.example.airbnbb7.authwithgoogle.dto.response;

import com.example.airbnbb7.db.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {

    private String email;

    private String token;

    private Role role;
}
