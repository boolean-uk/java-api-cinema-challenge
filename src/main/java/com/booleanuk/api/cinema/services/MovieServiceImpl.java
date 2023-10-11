package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.domain.dtos.*;
import com.booleanuk.api.cinema.domain.entities.Movie;
import com.booleanuk.api.cinema.errors.ResourceNotFoundException;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.utils.ErrorConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ScreeningService screeningService;
    private final ModelMapper modelMapper;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, ScreeningService screeningService, ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.screeningService = screeningService;
        this.modelMapper = modelMapper;
    }

    @Override
    public MovieResponseDTO createMovie(CreateMovieRequestDTO movieDTO) {
        Movie movie = modelMapper.map(movieDTO, Movie.class);
        LocalDateTime currentDateTime = LocalDateTime.now();
        movie.setCreatedAt(currentDateTime);
        movie.setUpdatedAt(currentDateTime);
        movie.setScreenings(new ArrayList<>());
        Movie savedMovie = movieRepository.save(movie);
        return modelMapper.map(savedMovie, MovieResponseDTO.class);
    }

    public MovieResponseDTO createMovieWithScreenings(CreateMovieWithScreeningsRequestDTO requestDTO) {
        CreateMovieRequestDTO movieDTO = modelMapper.map(requestDTO, CreateMovieRequestDTO.class);
        List<CreateScreeningRequestDTO> screenings = requestDTO.getScreenings();
        MovieResponseDTO createdMovie = createMovie(movieDTO);

        if (screenings != null && !screenings.isEmpty()) {
            for (CreateScreeningRequestDTO screeningDTO : screenings) {
                screeningService.createScreening(createdMovie.getId(), screeningDTO);
            }
        }
        return createdMovie;
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
            throw new ResourceNotFoundException(String.format(ErrorConstants.MOVIE_NOT_FOUND_MESSAGE, movieId));
        }
    }

    @Override
    public MovieResponseDTO updateMovie(Long movieId, UpdateMovieRequestDTO movieDTO) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isPresent()) {
            Movie existingMovie = movieOptional.get();

            if (hasUpdates(existingMovie, movieDTO)) {
                existingMovie.setUpdatedAt(LocalDateTime.now());
            }
            modelMapper.map(movieDTO, existingMovie);
            Movie updatedMovie = movieRepository.save(existingMovie);
            return modelMapper.map(updatedMovie, MovieResponseDTO.class);
        } else {
            throw new ResourceNotFoundException(String.format(ErrorConstants.MOVIE_NOT_FOUND_MESSAGE, movieId));
        }
    }

    @Override
    public MovieResponseDTO deleteMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorConstants.MOVIE_NOT_FOUND_MESSAGE, movieId)));

        movieRepository.delete(movie);

        return modelMapper.map(movie, MovieResponseDTO.class);
    }


    private boolean hasUpdates(Movie existingMovie, UpdateMovieRequestDTO movieDTO) {
        boolean hasUpdates = !existingMovie.getTitle().equals(movieDTO.getTitle()) ||
                !existingMovie.getRating().equals(movieDTO.getRating()) ||
                !existingMovie.getDescription().equals(movieDTO.getDescription()) ||
                existingMovie.getRuntimeMins() != movieDTO.getRuntimeMins();

        return hasUpdates;
    }
}
