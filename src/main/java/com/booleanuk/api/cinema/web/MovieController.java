package com.booleanuk.api.cinema.web;

import com.booleanuk.api.cinema.domain.dtos.CreateMovieRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.CustomerResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.MovieResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.UpdateMovieRequestDTO;
import com.booleanuk.api.cinema.errors.ResourceNotFoundException;
import com.booleanuk.api.cinema.responses.CustomerResponse;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.MovieResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.services.MovieService;
import com.booleanuk.api.cinema.utils.ErrorConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    //TODO: Refactor getAll methods

    //    @GetMapping
//    public ResponseEntity<List<MovieResponseDTO>> getAllMovies() {
//        List<MovieResponseDTO> movieResponseDTOs = movieService.getAllMovies();
//        return ResponseEntity.ok(movieResponseDTOs);
//    }
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
            error.set(ErrorConstants.MOVIE_NOT_FOUND_MESSAGE);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Response<?>> createMovie(@Valid @RequestBody CreateMovieRequestDTO movieDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            ErrorResponse error = new ErrorResponse();
            error.set(errorMessages);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        // TODO: Extract in Util class

        MovieResponseDTO responseDTO = this.movieService.createMovie(movieDTO);
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(responseDTO);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateMovie(@PathVariable Long id, @Valid @RequestBody UpdateMovieRequestDTO movieDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            ErrorResponse error = new ErrorResponse();
            error.set(errorMessages);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        try {
            MovieResponseDTO movieResponseDTO = movieService.updateMovie(id, movieDTO);
            return getResponseEntity(movieResponseDTO);
        } catch (ResourceNotFoundException e) {
            ErrorResponse error = new ErrorResponse();
            error.set(ErrorConstants.MOVIE_NOT_FOUND_MESSAGE);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteMovie(@PathVariable Long id) {
        try {
            MovieResponseDTO deletedMovie = movieService.deleteMovie(id);
            return getResponseEntity(deletedMovie);
        } catch (ResourceNotFoundException e) {
            ErrorResponse error = new ErrorResponse();
            error.set(ErrorConstants.CUSTOMER_NOT_FOUND_MESSAGE);
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
    // TODO: Do I really need this method??
}