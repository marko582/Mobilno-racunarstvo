package com.movietracker.controller;

import com.movietracker.dto.request.UserUpdateRequest;
import com.movietracker.dto.response.UserResponse;
import com.movietracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponse me() {
        return userService.getCurrentProfile();
    }

    @PutMapping("/me")
    public UserResponse updateMe(@Valid @RequestBody UserUpdateRequest request) {
        return userService.updateCurrentProfile(request);
    }
}
