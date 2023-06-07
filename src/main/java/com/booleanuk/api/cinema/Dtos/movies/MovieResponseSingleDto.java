package com.booleanuk.api.cinema.Dtos.movies;

import com.booleanuk.api.cinema.entities.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class MovieResponseSingleDto {
    private String status;
    private Movie data;
}
