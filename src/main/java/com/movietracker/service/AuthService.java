package com.movietracker.service;

import com.movietracker.config.JwtProperties;
import com.movietracker.dto.request.LoginRequest;
import com.movietracker.dto.request.RegisterRequest;
import com.movietracker.dto.response.AuthResponse;
import com.movietracker.dto.response.UserResponse;
import com.movietracker.entity.Role;
import com.movietracker.entity.User;
import com.movietracker.exception.DuplicateResourceException;
import com.movietracker.repository.UserRepository;
import com.movietracker.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;

    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username is already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email is already registered");
        }

        User user =
                User.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.USER)
                        .build();

        userRepository.save(user);
        return DtoMapper.toUserResponse(user);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));

        String token = jwtTokenProvider.generateToken(request.getUsername());
        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresInMs(jwtProperties.getExpirationMs())
                .build();
    }
}
