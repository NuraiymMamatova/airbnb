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
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserConverterResponse userConverterResponse;

    private final UserConverterRequest userConverterRequest;

    private final RoleRepository roleRepository;

    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        User user = mapToEntity(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        Role role = roleRepository.getById(2L);
        user.addRole(role);
        userRepository.save(user);
        return mapToResponse(user);
    }

    private User mapToEntity(UserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setImage(request.getImage());
        return user;
    }

    private UserResponse mapToResponse(User user) {
        if (user == null) {
            return null;
        }
        UserResponse response = new UserResponse();
        if (user.getId() != null) {
            response.setId(String.valueOf(user.getId()));
        }
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        return response;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("not found email"));
    }
}
