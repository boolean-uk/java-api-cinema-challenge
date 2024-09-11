package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/movies/{movieId}/screenings")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<List<Screening>> getAllScreeningsByMovieId(@PathVariable int movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        return ResponseEntity.ok(screeningRepository.findByMovie(movie));
    }

    @PostMapping
    public ResponseEntity<Screening> createScreening(@PathVariable int movieId, @RequestBody Screening screening) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        screening.setMovie(movie);
        Screening newScreening = screeningRepository.save(screening);
        return new ResponseEntity<>(newScreening, HttpStatus.CREATED);
    }
}