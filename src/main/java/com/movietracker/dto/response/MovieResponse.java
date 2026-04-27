package com.movietracker.dto.response;

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
public class MovieResponse {

    private Long id;
    private String title;
    private String description;
    private String genre;
    private Integer releaseYear;
    private Integer duration;
    private String imageUrl;
    private Double averageRating;
    private Long numberOfRatings;
}
