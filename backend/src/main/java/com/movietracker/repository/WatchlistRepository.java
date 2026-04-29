package com.movietracker.repository;

import com.movietracker.entity.Movie;
import com.movietracker.entity.User;
import com.movietracker.entity.Watchlist;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    Optional<Watchlist> findByUserAndMovie(User user, Movie movie);

    @EntityGraph(attributePaths = "movie")
    Page<Watchlist> findByUser(User user, Pageable pageable);
}
