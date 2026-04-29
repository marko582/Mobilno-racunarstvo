package com.movietracker.dto.response;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRatingResponse {

    private Long ratingId;
    private Integer stars;
    private Instant createdAt;
    private MovieResponse movie;
}
