package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.model.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getMovies();
    Movie getMovie(Long id);
    Movie createMovie(Movie movie);
    Movie updateMovie(Long id, Movie movie);
    Movie deleteMovie(Long id);
}
