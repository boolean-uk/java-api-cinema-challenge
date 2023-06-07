package com.booleanuk.api.cinema.dto;

import com.booleanuk.api.cinema.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class MovieViewDTO {

    private int id;
    private String title;
    private String rating;
    private String description;
    private int runtimeMins;

    private Date createdAt;
    private Date updatedAt;

    public static MovieViewDTO from(Movie movie){
        return new MovieViewDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getRating(),
                movie.getDescription(),
                movie.getRuntimeMins(),
                movie.getCreatedAt(),
                movie.getUpdatedAt()
        );
    }
}
