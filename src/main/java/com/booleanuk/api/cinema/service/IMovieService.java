package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.dto.MovieDTO;
import com.booleanuk.api.cinema.exceptions.EntityNotFoundException;
import com.booleanuk.api.cinema.model.Movie;

import java.util.List;

public interface IMovieService {

    List<Movie> getMovies();
    Movie insertMovie(MovieDTO movieDTO);
    Movie updateMovie(MovieDTO movieDTO) throws EntityNotFoundException;
    void deleteMovie(int id) throws EntityNotFoundException;
    Movie getMovieById(int id) throws EntityNotFoundException;
}
