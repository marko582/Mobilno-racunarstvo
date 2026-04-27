package com.movietracker.controller;

import com.movietracker.dto.response.WatchlistItemResponse;
import com.movietracker.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;

    @PostMapping("/{movieId}")
    @ResponseStatus(HttpStatus.CREATED)
    public WatchlistItemResponse add(@PathVariable Long movieId) {
        return watchlistService.add(movieId);
    }

    @DeleteMapping("/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long movieId) {
        watchlistService.remove(movieId);
    }

    @GetMapping
    public Page<WatchlistItemResponse> list(
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return watchlistService.listMine(pageable);
    }
}
