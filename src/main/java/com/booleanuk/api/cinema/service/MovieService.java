package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;

import java.util.List;

public interface MovieService {
    List<Movie> getMovies();
    Movie getMovie(long id);
    Movie createMovie(Movie movie);
    Movie updateMovie(long id, Movie movie);
    Movie deleteMovie(long id);
    Screening createScreening(long id ,Screening screening);
    List<Screening> getScreenings(long id);
}
