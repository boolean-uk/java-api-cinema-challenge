package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.payload.response.ErrorResponse;
import com.booleanuk.api.cinema.payload.response.MovieListResponse;
import com.booleanuk.api.cinema.payload.response.MovieResponse;
import com.booleanuk.api.cinema.payload.response.Response;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<MovieListResponse> getAll() {
        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.set(this.movieRepository.findAll());
        return ResponseEntity.ok(movieListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Movie movie) {
        MovieResponse movieResponse = new MovieResponse();
        try {
            movieResponse.set(this.movieRepository.save(movie));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getMovieById(@PathVariable int id) {
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie == null) {
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
        Movie updated = this.movieRepository.findById(id).orElse(null);
        if (updated == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        updated.setTitle(movie.getTitle());
        updated.setRating(movie.getRating());
        updated.setDescription(movie.getDescription());
        updated.setRuntimeMins(movie.getRuntimeMins());

        try {
            updated = this.movieRepository.save(updated);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(updated);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id) {
        Movie delete = this.movieRepository.findById(id).orElse(null);
        if (delete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.movieRepository.delete(delete);
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(delete);
        return ResponseEntity.ok(movieResponse);
    }
}
