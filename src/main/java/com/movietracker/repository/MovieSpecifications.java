package com.movietracker.repository;

import com.movietracker.entity.Movie;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public final class MovieSpecifications {

    private MovieSpecifications() {}

    public static Specification<Movie> filter(
            String genre, Integer releaseYear, Double averageRating, Integer duration) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (genre != null && !genre.isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("genre")), genre.toLowerCase()));
            }
            if (releaseYear != null) {
                predicates.add(cb.equal(root.get("releaseYear"), releaseYear));
            }
            if (averageRating != null) {
                predicates.add(cb.equal(root.get("averageRating"), averageRating));
            }
            if (duration != null) {
                predicates.add(cb.equal(root.get("duration"), duration));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
