package com.booleanuk.api.cinema.models.dto;

import com.booleanuk.api.cinema.models.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private int id;
    private String title;
    private String rating;
    private String description;
    private Integer runtimeMins;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static MovieDTO fromMovie(Movie movie) {
        return MovieDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .rating(movie.getRating())
                .description(movie.getDescription())
                .runtimeMins(movie.getRuntimeMins())
                .createdAt(movie.getCreatedAt())
                .updatedAt(movie.getUpdatedAt())
                .build();
    }

    public static List<MovieDTO> fromMovieList(List<Movie> movies) {
        return movies.stream()
                .map(MovieDTO::fromMovie)
                .collect(Collectors.toList());
    }
}