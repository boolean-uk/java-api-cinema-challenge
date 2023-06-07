package com.booleanuk.api.cinema.services.movie;

import com.booleanuk.api.cinema.entities.Movie;

import java.util.List;

public interface MovieServiceInterface {
    Movie createMovie(Movie movie);
    List<Movie> getAllMovie();

    Movie updateMovie(int id,Movie movie);

    Movie deleteMovie(int id);
}
