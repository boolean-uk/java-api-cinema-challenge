package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.domain.dtos.CreateMovieRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.MovieResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.UpdateMovieRequestDTO;
import com.booleanuk.api.cinema.domain.entities.Movie;
import com.booleanuk.api.cinema.errors.ResourceNotFoundException;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MovieResponseDTO createMovie(CreateMovieRequestDTO movieDTO) {
        Movie movie = modelMapper.map(movieDTO, Movie.class);
        Movie savedMovie = movieRepository.save(movie);
        return modelMapper.map(savedMovie, MovieResponseDTO.class);
    }

    @Override
    public List<MovieResponseDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(movie -> modelMapper.map(movie, MovieResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MovieResponseDTO getMovieById(Long movieId) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isPresent()) {
            return modelMapper.map(movieOptional.get(), MovieResponseDTO.class);
        } else {
            throw new ResourceNotFoundException("Movie not found with id: " + movieId);
        }
    }

    @Override
    public MovieResponseDTO updateMovie(Long movieId, UpdateMovieRequestDTO movieDTO) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isPresent()) {
            Movie existingMovie = movieOptional.get();
            modelMapper.map(movieDTO, existingMovie);
            Movie updatedMovie = movieRepository.save(existingMovie);
            return modelMapper.map(updatedMovie, MovieResponseDTO.class);
        } else {
            throw new ResourceNotFoundException("Movie not found with id: " + movieId);
        }
    }

    @Override
    public void deleteMovie(Long movieId) {
        if (movieRepository.existsById(movieId)) {
            movieRepository.deleteById(movieId);
        } else {
            throw new ResourceNotFoundException("Movie not found with id: " + movieId);
        }
    }
}
