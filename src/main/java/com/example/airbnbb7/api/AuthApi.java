package com.example.airbnbb7.api;

import com.example.airbnbb7.converter.login.LoginConverter;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.dto.request.UserRequest;
import com.example.airbnbb7.dto.response.LoginResponse;
import com.example.airbnbb7.dto.response.UserResponse;
import com.example.airbnbb7.repository.UserRepository;
import com.example.airbnbb7.security.ValidationExceptionType;
import com.example.airbnbb7.security.jwt.JwtTokenUtil;
import com.example.airbnbb7.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt/")
public class AuthApi {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final LoginConverter loginConverter;

    @PostMapping ("login")
    public ResponseEntity<LoginResponse> getLogin(@RequestBody UserRequest request) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(request.getEmail(),
                            request.getPassword());
            User user = userRepository.findByEmail(token.getName()).get();
            return ResponseEntity.ok().body(loginConverter.
                    loginView(jwtTokenUtil.generateToken(user),
                            ValidationExceptionType.SUCCESSFUL, user));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                    body(loginConverter.
                            loginView("", ValidationExceptionType
                                    .LOGIN_FAILED, null));
        }
    }

    @PostMapping("registration")
    public UserResponse create(@Valid @RequestBody UserRequest request) throws IOException {
        for (int i = 0; i < userRepository.findAll().size(); i++) {
            if (!Objects.equals(userRepository.findAll().get(i).getEmail(), request.getEmail())) {
                return userService.saveUser(request);
            }
        }
        return null;
    }
}
