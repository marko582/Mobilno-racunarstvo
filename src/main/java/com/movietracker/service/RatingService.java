package com.movietracker.service;

import com.movietracker.dto.request.RatingRequest;
import com.movietracker.dto.response.UserRatingResponse;
import com.movietracker.entity.Movie;
import com.movietracker.entity.Rating;
import com.movietracker.entity.User;
import com.movietracker.repository.MovieRepository;
import com.movietracker.repository.RatingRepository;
import com.movietracker.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final MovieRepository movieRepository;
    private final MovieService movieService;
    private final SecurityUtils securityUtils;

    @Transactional
    public UserRatingResponse rateMovie(Long movieId, RatingRequest request) {
        User user = securityUtils.currentUser();
        Movie movie = movieService.getMovieEntity(movieId);

        Rating rating =
                ratingRepository
                        .findByUserAndMovie(user, movie)
                        .orElseGet(
                                () ->
                                        Rating.builder()
                                                .user(user)
                                                .movie(movie)
                                                .stars(request.getStars())
                                                .build());

        rating.setStars(request.getStars());
        ratingRepository.save(rating);

        refreshMovieAggregates(movie);

        return DtoMapper.toUserRatingResponse(rating);
    }

    @Transactional(readOnly = true)
    public Page<UserRatingResponse> listMine(Pageable pageable) {
        User user = securityUtils.currentUser();
        return ratingRepository.findByUser(user, pageable).map(DtoMapper::toUserRatingResponse);
    }

    private void refreshMovieAggregates(Movie movie) {
        double avg = ratingRepository.averageStarsByMovieId(movie.getId());
        long count = ratingRepository.countByMovie_Id(movie.getId());
        movie.setAverageRating(Math.round(avg * 100.0) / 100.0);
        movie.setRatingCount(count);
        movieRepository.save(movie);
    }
}
