package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies/{movieId}/screenings")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    private final Response<Object> response = new Response<>();

    @GetMapping
    public ResponseEntity<?> getAllScreeningsByMovieId(@PathVariable int movieId) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            response.setError("not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.setSuccess(movie.getScreenings());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createScreening(@PathVariable int movieId, @RequestBody Screening screening) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            response.setError("not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            screening.setMovie(movie);
            Screening newScreening = screeningRepository.save(screening);
            response.setSuccess(newScreening);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setError("bad request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}