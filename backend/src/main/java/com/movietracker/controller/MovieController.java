package com.movietracker.controller;

import com.movietracker.dto.response.MovieResponse;
import com.movietracker.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public Page<MovieResponse> list(@PageableDefault(size = 20) Pageable pageable) {
        return movieService.findAll(pageable);
    }

    @GetMapping("/search")
    public Page<MovieResponse> search(
            @RequestParam(required = false) String title,
            @PageableDefault(size = 20) Pageable pageable) {
        return movieService.searchByTitle(title, pageable);
    }

    @GetMapping("/filter")
    public Page<MovieResponse> filter(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false) Double averageRating,
            @RequestParam(required = false) Integer duration,
            @PageableDefault(size = 20) Pageable pageable) {
        return movieService.filter(genre, releaseYear, averageRating, duration, pageable);
    }

    @GetMapping("/{id}")
    public MovieResponse getById(@PathVariable Long id) {
        return movieService.findById(id);
    }
}
