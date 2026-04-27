package com.movietracker.controller;

import com.movietracker.dto.request.RatingRequest;
import com.movietracker.dto.response.UserRatingResponse;
import com.movietracker.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/{movieId}")
    @ResponseStatus(HttpStatus.OK)
    public UserRatingResponse rate(
            @PathVariable Long movieId, @Valid @RequestBody RatingRequest request) {
        return ratingService.rateMovie(movieId, request);
    }

    @GetMapping("/me")
    public Page<UserRatingResponse> myRatings(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
                    Pageable pageable) {
        return ratingService.listMine(pageable);
    }
}
