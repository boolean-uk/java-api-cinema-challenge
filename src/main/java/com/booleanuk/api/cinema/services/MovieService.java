package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.domain.dtos.CreateMovieRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.CreateMovieWithScreeningsRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.MovieResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.UpdateMovieRequestDTO;

import java.util.List;

public interface MovieService {
    MovieResponseDTO createMovie(CreateMovieRequestDTO movieDTO);

    List<MovieResponseDTO> getAllMovies();

    MovieResponseDTO getMovieById(Long movieId);

    MovieResponseDTO updateMovie(Long movieId, UpdateMovieRequestDTO movieDTO);

    MovieResponseDTO deleteMovie(Long movieId);
    MovieResponseDTO createMovieWithScreenings(CreateMovieWithScreeningsRequestDTO requestDTO);
}