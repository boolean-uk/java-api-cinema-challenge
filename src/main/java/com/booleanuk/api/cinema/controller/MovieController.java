package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.MovieListResponse;
import com.booleanuk.api.cinema.response.MovieResponse;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<MovieListResponse> getMovies() {
        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.set(this.movieRepository.findAll());
        return ResponseEntity.ok(movieListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable int id) {
        Movie movie = null;
        movie = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry movie with this id, does not exist"));
        return ResponseEntity.ok(movie);
    }

    //Couldn't get to make it work to also create a new screenings entry if a screeningNumber etc. was provided
    @PostMapping
    public ResponseEntity<Response<?>> createMovie(@RequestBody Movie movie) {
        MovieResponse movieResponse = new MovieResponse();
        if (movie.getTitle() == null|| movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() == 0) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        try {
            movieResponse.set(this.movieRepository.save(movie));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.movieRepository.findById(id).orElse(null);

        if (movieToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        try {
            movieToUpdate = this.movieRepository.findById(id).orElse(null);
        } catch (Exception e) {
            ErrorResponse secondError = new ErrorResponse();
            secondError.set("bad request");
            return new ResponseEntity<>(secondError, HttpStatus.BAD_REQUEST);
        }
        if (movie.getTitle() != null) {
            movieToUpdate.setTitle(movie.getTitle());
        }
        if (movie.getRating() != null){
            movieToUpdate.setRating(movie.getRating());
        }
        if (movie.getDescription() != null) {
            movieToUpdate.setDescription(movie.getDescription());
        }
        if (movie.getRuntimeMins() != 0) {
            movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        }

        this.movieRepository.save(movieToUpdate);

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movieToUpdate);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.movieRepository.findById(id).orElse(null);
        if (movieToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.movieRepository.delete(movieToDelete);
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movieToDelete);
        return ResponseEntity.ok(movieResponse);
    }
}
