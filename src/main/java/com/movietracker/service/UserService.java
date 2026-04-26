package com.movietracker.service;

import com.movietracker.dto.request.UserUpdateRequest;
import com.movietracker.dto.response.UserResponse;
import com.movietracker.entity.User;
import com.movietracker.exception.DuplicateResourceException;
import com.movietracker.repository.UserRepository;
import com.movietracker.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    @Transactional(readOnly = true)
    public UserResponse getCurrentProfile() {
        User user = securityUtils.currentUser();
        return DtoMapper.toUserResponse(user);
    }

    @Transactional
    public UserResponse updateCurrentProfile(UserUpdateRequest request) {
        User user = securityUtils.currentUser();

        if (StringUtils.hasText(request.getFirstName())) {
            user.setFirstName(request.getFirstName());
        }
        if (StringUtils.hasText(request.getLastName())) {
            user.setLastName(request.getLastName());
        }
        if (StringUtils.hasText(request.getUsername())) {
            String newUsername = request.getUsername();
            if (!newUsername.equalsIgnoreCase(user.getUsername())
                    && userRepository.existsByUsername(newUsername)) {
                throw new DuplicateResourceException("Username is already taken");
            }
            user.setUsername(newUsername);
        }
        if (StringUtils.hasText(request.getEmail())) {
            String email = request.getEmail();
            if (!email.equalsIgnoreCase(user.getEmail())
                    && userRepository.existsByEmail(email)) {
                throw new DuplicateResourceException("Email is already registered");
            }
            user.setEmail(email);
        }

        userRepository.save(user);
        return DtoMapper.toUserResponse(user);
    }
}
