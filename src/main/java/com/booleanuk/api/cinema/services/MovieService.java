package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.Dtos.MovieDto;
import com.booleanuk.api.cinema.Dtos.ScreeningDto;
import com.booleanuk.api.cinema.entities.Movie;
import com.booleanuk.api.cinema.entities.Screening;

import java.util.List;

public interface MovieService {
    Movie createMovie(MovieDto requestMovie);

    List<Movie> getAllMovies();

    Movie updateMovie(int id, Movie requestMovie);

    Movie deleteMovie(int id);

    Screening createScreeningForMovie(int movieId, ScreeningDto requestScreening);

    List<Screening> getAllScreeningForMovie(int movieId);
}
