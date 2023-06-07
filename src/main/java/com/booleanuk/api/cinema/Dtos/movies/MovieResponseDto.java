package com.booleanuk.api.cinema.Dtos.movies;

import com.booleanuk.api.cinema.entities.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MovieResponseDto {
    private String status;
    private List<Movie> data;
}
