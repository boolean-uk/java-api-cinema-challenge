package com.booleanuk.api.cinema.dto;

import com.booleanuk.api.cinema.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieViewDTO {

    private int id;
    private String title;
    private String rating;
    private String description;
    private int runtimeMins;

    public static MovieViewDTO from(Movie movie){
        return new MovieViewDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getRating(),
                movie.getDescription(),
                movie.getRuntimeMins()
        );
    }
}
