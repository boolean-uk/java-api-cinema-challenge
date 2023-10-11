package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.MovieListResponse;
import com.booleanuk.api.cinema.response.MovieResponse;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<MovieListResponse> getAllMovies() {
        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.set(this.movieRepository.findAll());
        return ResponseEntity.ok(movieListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> addMovie(@RequestBody Movie movie) {
        MovieResponse movieResponse = new MovieResponse();
        try {
            movieResponse.set(this.movieRepository.save(movie));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad Request");
            return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(movieResponse,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateMovie(@RequestBody Movie movie, @PathVariable int id) {
        Movie movieToUpdate = null;
        try {
            movieToUpdate = this.movieRepository.findById(id).orElse(null);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad Request");
            return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
        }
        if (movieToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Not Found");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }
        movieToUpdate.setUpdatedAt(java.time.LocalDateTime.now());
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        movieToUpdate = this.movieRepository.save(movieToUpdate);
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movieToUpdate);
        return new ResponseEntity<>(movieResponse,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteMovie(@PathVariable int id){
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Movie Not Found");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }
        this.movieRepository.delete(movie);
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movie);
        return new ResponseEntity<>(movieResponse,HttpStatus.OK);
    }
}
