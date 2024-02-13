package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<MovieListResponse> getAllMovies(){
        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.set(this.movieRepository.findAll());
        return ResponseEntity.ok(movieListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createMovie(@RequestBody Movie movie) {
        Movie movie1;
        try {
            movie1 = this.movieRepository.save(movie);
            for (Screening screening : movie.getScreenings()){
                screening.setMovie(movie);
            }
            List<Screening> screenings = screeningRepository.saveAll(movie.getScreenings());
            movie.setScreenings(screenings);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not create a new movie, please check all fields are correct.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movie1);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateMovie(@PathVariable int id, @RequestBody Movie movie){
        Movie movie1 = this.getAMovie(id);
        if (movie1 == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        } try {
            movie1.setTitle(movie.getTitle());
            movie1.setRating(movie.getRating());
            movie1.setDescription(movie.getDescription());
            movie1.setRuntimeMins(movie.getRuntimeMins());
            movie1.setUpdatedAt(ZonedDateTime.now());
            this.movieRepository.save(movie1);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not update movie, please check all fields are correct.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movie1);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteMovie(@PathVariable int id){
        Movie movie1 = this.getAMovie(id);
        if (movie1 == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.movieRepository.delete(movie1);
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movie1);
        return ResponseEntity.ok(movieResponse);
    }
    private Movie getAMovie(int id){
        return this.movieRepository.findById(id)
                .orElse(null);
    }
}
