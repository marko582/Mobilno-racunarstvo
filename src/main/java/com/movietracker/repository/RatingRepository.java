package com.movietracker.repository;

import com.movietracker.entity.Movie;
import com.movietracker.entity.Rating;
import com.movietracker.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByUserAndMovie(User user, Movie movie);

    @EntityGraph(attributePaths = "movie")
    Page<Rating> findByUser(User user, Pageable pageable);

    @Query("SELECT COALESCE(AVG(r.stars), 0) FROM Rating r WHERE r.movie.id = :movieId")
    double averageStarsByMovieId(@Param("movieId") Long movieId);

    long countByMovie_Id(Long movieId);
}
