package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.MovieListResponse;
import com.booleanuk.api.cinema.response.MovieResponse;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Movie movie) {

        if (
                movie.getTitle() == null ||
                movie.getRating() == null ||
                movie.getDescription() == null ||
                movie.getRuntimeMins() <= 0
        ) {
            ErrorResponse badRequest = new ErrorResponse();
            badRequest.set("bad request");
            return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
        }
        if (movie.getScreenings() != null && (!movie.getScreenings().isEmpty())) {
            for(Screening screening : movie.getScreenings()) {
                screening.setCreatedAt(String.valueOf(LocalDateTime.now()));
                screening.setUpdatedAt(screening.getCreatedAt());
                screening.setMovie(movie);
            }
        } else {
            movie.setScreenings(new ArrayList<Screening>());
        }
        movie.setCreatedAt(String.valueOf(LocalDateTime.now()));
        movie.setUpdatedAt(movie.getCreatedAt());
        Movie createdMovie = this.movieRepository.save(movie);
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(createdMovie);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<MovieListResponse> getAll() {
        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.set(this.movieRepository.findAll());
        return ResponseEntity.ok(movieListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getById(@PathVariable int id) {
        Movie movie = null;
        movie = this.movieRepository.findById(id).orElse(null);
        if(movie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movie);
        return ResponseEntity.ok(movieResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.movieRepository.findById(id).orElse(null);
        if(movieToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Optional.ofNullable(movie.getTitle())
                .ifPresent(title -> movieToUpdate.setTitle(title));
        Optional.ofNullable(movie.getDescription())
                .ifPresent(description -> movieToUpdate.setDescription(description));
        Optional.ofNullable(movie.getRating())
                .ifPresent(rating -> movieToUpdate.setRating(rating));
        Optional.ofNullable(movie.getRuntimeMins())
                .ifPresent(runtime -> movieToUpdate.setTitle(String.valueOf(runtime)));
        movieToUpdate.setUpdatedAt(String.valueOf(LocalDateTime.now()));
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movie);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id) {
        Movie movieToDelete = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(movieToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.movieRepository.delete(movieToDelete);
        movieToDelete.setScreenings(new ArrayList<Screening>());
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movieToDelete);
        return ResponseEntity.ok(movieResponse);
    }

}
