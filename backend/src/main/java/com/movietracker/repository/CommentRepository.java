package com.movietracker.repository;

import com.movietracker.entity.Comment;
import com.movietracker.entity.Movie;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = "user")
    List<Comment> findByMovieOrderByCreatedAtAsc(Movie movie);
}
