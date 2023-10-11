package com.booleanuk.api.cinema.web;

import com.booleanuk.api.cinema.domain.dtos.*;
import com.booleanuk.api.cinema.errors.ResourceNotFoundException;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.MovieResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.services.MovieService;
import com.booleanuk.api.cinema.utils.ErrorConstants;
import com.booleanuk.api.cinema.utils.ErrorUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping
    public ResponseEntity<Response<List<MovieResponseDTO>>> getAllMovies() {
        List<MovieResponseDTO> movieResponseDTOs = movieService.getAllMovies();
        Response<List<MovieResponseDTO>> response = new Response<>();
        response.set(movieResponseDTOs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getMovieById(@PathVariable Long id) {
        try {
            MovieResponseDTO movieResponseDTO = movieService.getMovieById(id);
            return getResponseEntity(movieResponseDTO);
        } catch (ResourceNotFoundException e) {
            ErrorResponse error = new ErrorResponse();
            error.set(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Response<?>> createMovieWithScreenings(@Valid @RequestBody CreateMovieWithScreeningsRequestDTO requestDTO, BindingResult bindingResult) {
        ResponseEntity<Response<?>> error = ErrorUtil.getErrors(bindingResult);
        if (error != null) return error;
        MovieResponseDTO movieResponseDTO = movieService.createMovieWithScreenings(requestDTO);
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movieResponseDTO);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateMovie(@PathVariable Long id, @Valid @RequestBody UpdateMovieRequestDTO movieDTO, BindingResult bindingResult) {
        ResponseEntity<Response<?>> error = ErrorUtil.getErrors(bindingResult);
        if (error != null) return error;
        try {
            MovieResponseDTO movieResponseDTO = movieService.updateMovie(id, movieDTO);
            return getResponseEntity(movieResponseDTO);
        } catch (ResourceNotFoundException e) {
            ErrorResponse errorNotFound = new ErrorResponse();
            errorNotFound.set(e.getMessage());
            return new ResponseEntity<>(errorNotFound, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteMovie(@PathVariable Long id) {
        try {
            MovieResponseDTO deletedMovie = movieService.deleteMovie(id);
            return getResponseEntity(deletedMovie);
        } catch (ResourceNotFoundException e) {
            ErrorResponse error = new ErrorResponse();
            error.set(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<Response<?>> getResponseEntity(MovieResponseDTO movie) {
        if (movie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set(ErrorConstants.MOVIE_NOT_FOUND_MESSAGE);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movie);
        return ResponseEntity.ok(movieResponse);
    }
}