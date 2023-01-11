package com.example.airbnbb7.service.serviceImpl;

import com.example.airbnbb7.converter.user.UserConverterRequest;
import com.example.airbnbb7.converter.user.UserConverterResponse;
import com.example.airbnbb7.db.entities.Role;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.dto.request.UserRequest;
import com.example.airbnbb7.dto.response.UserResponse;
import com.example.airbnbb7.repository.RoleRepository;
import com.example.airbnbb7.repository.UserRepository;
import com.example.airbnbb7.service.UserService;
import com.example.airbnbb7.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.apache.el.util.Validation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserConverterResponse userConverterResponse;

    private final UserConverterRequest userConverterRequest;

    private final RoleRepository roleRepository;

    @Override
    public UserResponse saveUser(UserRequest userRequest) throws IOException {
        UserValidator.validator(userRequest.getPassword(),userRequest.getName());
        if (!UserValidator.isValidEmail(userRequest.getEmail())){
            throw new IOException("invalid account!");
        }
        User user = userConverterRequest.create(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        Role role = roleRepository.getById(2L);
        user.addRole(role);
        userRepository.save(user);
        return userConverterResponse.create(user);
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
            request.setPassword(passwordEncoder.encode("1234567"));
            request.setName("Esen");

            User user2 = userConverterRequest.create(request);

            user2.setRoles(Arrays.asList(role1));
            role1.setUsers(Arrays.asList(user2));

            userRepository.save(user2);
            roleRepository.save(role1);
            roleRepository.save(role2);

        }
    }
}