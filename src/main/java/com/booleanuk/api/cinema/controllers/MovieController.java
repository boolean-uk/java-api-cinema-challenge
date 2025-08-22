package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.payload.response.*;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

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
    public ResponseEntity<Response<?>> createMovie(@RequestBody Movie movie) {
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


    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateMovie (@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.movieRepository.findById(id).orElse(null);
        if (movieToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that ID found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        if (movie.getTitle() == null && movie.getRating() == null && movie.getDescription() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not update the movie, please check all fields are correct");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        if (movie.getTitle() != null){
            movieToUpdate.setTitle(movie.getTitle());
        }
        if(movie.getRating() != null) {
            movieToUpdate.setRating(movie.getRating());
        }
        if(movie.getDescription() != null) {
            movieToUpdate.setDescription(movie.getDescription());
        }

        if (movie.getRuntimeMins() > 0) {
            movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        }

        movieToUpdate.setUpdatedAt(OffsetDateTime.now());

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(this.movieRepository.save(movieToUpdate));
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.movieRepository.findById(id).orElse(null);
        if (movieToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that ID found to delete");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        this.movieRepository.delete(movieToDelete);
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movieToDelete);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

}
