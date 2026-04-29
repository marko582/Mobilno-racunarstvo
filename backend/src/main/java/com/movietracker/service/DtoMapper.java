package com.movietracker.service;

import com.movietracker.dto.response.CommentResponse;
import com.movietracker.dto.response.MovieResponse;
import com.movietracker.dto.response.UserRatingResponse;
import com.movietracker.dto.response.UserResponse;
import com.movietracker.dto.response.WatchlistItemResponse;
import com.movietracker.entity.Comment;
import com.movietracker.entity.Movie;
import com.movietracker.entity.Rating;
import com.movietracker.entity.User;
import com.movietracker.entity.Watchlist;

public final class DtoMapper {

    private DtoMapper() {}

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public static MovieResponse toMovieResponse(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .genre(movie.getGenre())
                .releaseYear(movie.getReleaseYear())
                .duration(movie.getDuration())
                .imageUrl(movie.getImageUrl())
                .averageRating(movie.getAverageRating())
                .numberOfRatings(movie.getRatingCount())
                .build();
    }

    public static CommentResponse toCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .username(comment.getUser().getUsername())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    public static UserRatingResponse toUserRatingResponse(Rating rating) {
        return UserRatingResponse.builder()
                .ratingId(rating.getId())
                .stars(rating.getStars())
                .createdAt(rating.getCreatedAt())
                .movie(toMovieResponse(rating.getMovie()))
                .build();
    }

    public static WatchlistItemResponse toWatchlistItemResponse(Watchlist entry) {
        return WatchlistItemResponse.builder()
                .watchlistId(entry.getId())
                .dateAdded(entry.getCreatedAt())
                .movie(toMovieResponse(entry.getMovie()))
                .build();
    }
}
