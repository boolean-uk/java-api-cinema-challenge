package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.model.Movie;

import java.util.List;

public class MovieListResponse extends Response<List<Movie>>{
    public void set(List<Movie> list) {
        this.data = list;
    }
}
