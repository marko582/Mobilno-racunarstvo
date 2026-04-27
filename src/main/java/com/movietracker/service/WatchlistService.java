package com.movietracker.service;

import com.movietracker.dto.response.WatchlistItemResponse;
import com.movietracker.entity.Movie;
import com.movietracker.entity.User;
import com.movietracker.entity.Watchlist;
import com.movietracker.exception.DuplicateResourceException;
import com.movietracker.exception.ResourceNotFoundException;
import com.movietracker.repository.WatchlistRepository;
import com.movietracker.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final MovieService movieService;
    private final SecurityUtils securityUtils;

    @Transactional
    public WatchlistItemResponse add(Long movieId) {
        User user = securityUtils.currentUser();
        Movie movie = movieService.getMovieEntity(movieId);

        if (watchlistRepository.findByUserAndMovie(user, movie).isPresent()) {
            throw new DuplicateResourceException("Movie is already in your watchlist");
        }

        Watchlist entry =
                Watchlist.builder().user(user).movie(movie).build();
        watchlistRepository.save(entry);
        return DtoMapper.toWatchlistItemResponse(entry);
    }

    @Transactional
    public void remove(Long movieId) {
        User user = securityUtils.currentUser();
        Movie movie = movieService.getMovieEntity(movieId);
        Watchlist entry =
                watchlistRepository
                        .findByUserAndMovie(user, movie)
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Movie is not in your watchlist: " + movieId));
        watchlistRepository.delete(entry);
    }

    @Transactional(readOnly = true)
    public Page<WatchlistItemResponse> listMine(Pageable pageable) {
        User user = securityUtils.currentUser();
        return watchlistRepository.findByUser(user, pageable).map(DtoMapper::toWatchlistItemResponse);
    }
}
