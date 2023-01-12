package com.example.airbnbb7.service.serviceImpl;

import com.example.airbnbb7.converter.login.LoginConverter;
import com.example.airbnbb7.converter.user.UserConverterRequest;
import com.example.airbnbb7.db.entities.Role;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.dto.request.UserRequest;
import com.example.airbnbb7.dto.response.LoginResponse;
import com.example.airbnbb7.exceptions.InvalidCredentialException;
import com.example.airbnbb7.exceptions.NotFoundException;
import com.example.airbnbb7.repository.RoleRepository;
import com.example.airbnbb7.repository.UserRepository;
import com.example.airbnbb7.security.ValidationExceptionType;
import com.example.airbnbb7.security.jwt.JwtTokenUtil;
import com.example.airbnbb7.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConverterRequest userConverterRequest;
    private final RoleRepository roleRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final LoginConverter loginConverter;


    public ResponseEntity<LoginResponse> getLogin(@RequestBody UserRequest request) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(request.getEmail(),
                        request.getPassword());
        User user = userRepository.findByEmail(token.getName()).orElseThrow(
                () -> {
                    throw new NotFoundException(String.format("the user with this email was not found", request.getEmail()));
                });
        if (request.getPassword() == null) {
            throw new NotFoundException("Password must not be empty");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException("invalid password");
        }
        return ResponseEntity.ok().body(loginConverter.
                loginView(jwtTokenUtil.generateToken(user),
                        ValidationExceptionType.SUCCESSFUL, user));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("not found email"));
    }

    @PostConstruct
    public void initMethod() {
        if (roleRepository.findAll().size() == 0 && roleRepository.findAll().size() == 0) {
            Role role1 = new Role();
            role1.setNameOfRole("Admin");

            Role role2 = new Role();
            role2.setNameOfRole("User");

            UserRequest request = new UserRequest();
            request.setEmail("esen@gmail.com");
            request.setPassword(passwordEncoder.encode("12345678"));

            User user2 = userConverterRequest.create(request);

            user2.setRoles(Arrays.asList(role1));
            role1.setUsers(Arrays.asList(user2));

            userRepository.save(user2);
            roleRepository.save(role1);
            roleRepository.save(role2);

        }
    }
}