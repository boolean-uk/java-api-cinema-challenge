package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.model.Movie;
import java.util.List;

public class MovieListResponse extends Response<List<Movie>> {
    private List<Movie> data;
    public MovieListResponse(List<Movie> data) {
        super("success", data);
    }
}

