package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.movie.Movie;

public class MovieResponse extends Response<Movie>{

    public MovieResponse(Movie movie) {
        super("success", movie);
    }
}
