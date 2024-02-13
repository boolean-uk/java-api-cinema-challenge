package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.response.*;
import com.booleanuk.api.cinema.response.movie.MovieListResponse;
import com.booleanuk.api.cinema.response.movie.MovieResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<MovieListResponse> getAllMovies() {
        List<Movie> movies = this.movieRepository.findAll();
        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.set(movies);
        return ResponseEntity.ok(movieListResponse);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getMovieById(@PathVariable int id) {
        Movie returnMovie = this.movieRepository.findById(id).orElse(null);
        if (returnMovie == null ) {
            ErrorResponse error = new ErrorResponse();
            error.set("No movies matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(returnMovie);
        return ResponseEntity.ok(movieResponse);

    }

    @PostMapping
    public ResponseEntity<Response<?>> createMovie(@RequestBody Movie movie) {
        if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null
            || movie.getRuntimeMins() < 0) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create the movie, please check all required fields");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Movie createdMovie = this.movieRepository.save(movie);
        createdMovie.setScreenings(new ArrayList<>());

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(createdMovie);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null
            || movie.getRuntimeMins() < 0) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not update the movie's details, please check all required fields");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Movie movieToUpdate = this.movieRepository.findById(id).orElse(null);
        if(movieToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No movies matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        movieToUpdate.setCreatedAt(movie.getCreatedAt());
        movieToUpdate.setUpdatedAt(movie.getUpdatedAt());
        movieToUpdate.setScreenings(new ArrayList<>());

        Movie alteredMovie = this.movieRepository.save(movieToUpdate);
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(alteredMovie);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.movieRepository.findById(id).orElse(null);

        if (movieToDelete == null ) {
            ErrorResponse error = new ErrorResponse();
            error.set("No movies matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        this.movieRepository.delete(movieToDelete);
        movieToDelete.setScreenings(new ArrayList<Screening>());

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movieToDelete);
        return ResponseEntity.ok(movieResponse);
    }
}
