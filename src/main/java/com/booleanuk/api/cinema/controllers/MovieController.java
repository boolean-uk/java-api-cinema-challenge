package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.payload.response.*;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<MovieListResponse> getAllMovies() {
        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.set(this.movieRepository.findAll());
        return ResponseEntity.ok(movieListResponse);
    }

    public record PostMovie(String title, String rating, String description, Integer runtimeMins, PostScreening[] screenings) {}

    public record PostScreening(int screenNumber, int capacity, String startsAt) {}

    @PostMapping
    public ResponseEntity<Response<?>> createMovie(@RequestBody PostMovie request) {
        MovieResponse movieResponse = new MovieResponse();
        Movie movie = new Movie(request.title(), request.rating(), request.description(), request.runtimeMins(), OffsetDateTime.now());
        try {
            movieResponse.set(this.movieRepository.save(movie));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");
        for (PostScreening scr: request.screenings())
            try {
                this.screeningRepository.save(new Screening(movie, scr.screenNumber(), OffsetDateTime.parse(scr.startsAt(), formatter),scr.capacity()));
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

    public record PutMovie(String title, String rating, String description, Integer runtimeMins) {}

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateMovie(@PathVariable int id, @RequestBody PutMovie movie) {
        Movie movieToUpdate = this.movieRepository.findById(id).orElse(null);
        if (movieToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        movieToUpdate.setTitle(movie.title());
        movieToUpdate.setRating(movie.rating());
        movieToUpdate.setDescription(movie.description());
        movieToUpdate.setRuntimeMins(movie.runtimeMins());
        movieToUpdate.setUpdatedAt(OffsetDateTime.now());

        try {
            movieToUpdate = this.movieRepository.save(movieToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
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
