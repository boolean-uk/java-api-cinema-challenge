package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.movie.Movie;

import java.util.List;

public class MovieListResponse extends Response<List<Movie>>{

    public MovieListResponse(List<Movie> data) {
        super("success", data);
    }
}
