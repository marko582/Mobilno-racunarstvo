package com.movietracker.service;

import com.movietracker.dto.response.MovieResponse;
import com.movietracker.entity.Movie;
import com.movietracker.exception.ResourceNotFoundException;
import com.movietracker.repository.MovieRepository;
import com.movietracker.repository.MovieSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    @Transactional(readOnly = true)
    public Page<MovieResponse> findAll(Pageable pageable) {
        return movieRepository.findAll(pageable).map(DtoMapper::toMovieResponse);
    }

    @Transactional(readOnly = true)
    public MovieResponse findById(Long id) {
        Movie movie =
                movieRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
        return DtoMapper.toMovieResponse(movie);
    }

    @Transactional(readOnly = true)
    public Page<MovieResponse> searchByTitle(String title, Pageable pageable) {
        if (title == null || title.isBlank()) {
            return findAll(pageable);
        }
        return movieRepository
                .findByTitleContainingIgnoreCase(title.trim(), pageable)
                .map(DtoMapper::toMovieResponse);
    }

    @Transactional(readOnly = true)
    public Page<MovieResponse> filter(
            String genre, Integer releaseYear, Double averageRating, Integer duration, Pageable pageable) {

        Specification<Movie> spec = MovieSpecifications.filter(genre, releaseYear, averageRating, duration);
        return movieRepository.findAll(spec, pageable).map(DtoMapper::toMovieResponse);
    }

    @Transactional(readOnly = true)
    public Movie getMovieEntity(Long id) {
        return movieRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
    }
}
