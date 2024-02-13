package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.model.MovieDTO;

public class MovieResponse extends Response<MovieDTO> {
    private MovieDTO data;
    public MovieResponse(MovieDTO movie) {
        super("success", movie);
    }
}