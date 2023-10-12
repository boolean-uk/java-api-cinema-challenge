package com.booleanuk.api.cinema.Dtos;

import com.booleanuk.api.cinema.entities.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class MovieDto {
    private String title;
    private String rating;
    private String description;
    private Integer runtimeMins;
    private List<ScreeningDto> screenings;

    public Movie toMovie() {
        return new Movie(0, title, rating, description, runtimeMins, null, null, new ArrayList<>());
    }

}
