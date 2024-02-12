package com.booleanuk.api.cinema.response;
import com.booleanuk.api.cinema.model.Movie;

public class MovieResponse extends Response<Movie> {
    private Movie data;
    public MovieResponse(Movie movie) {
        super("Success",movie);
    }
}