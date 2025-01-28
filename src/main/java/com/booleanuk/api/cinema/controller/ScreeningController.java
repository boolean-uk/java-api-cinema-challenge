package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/movies/{id}/screenings")
public class ScreeningController {
    ScreeningRepository screeningRepository;
    MovieRepository movieRepository;

    public ScreeningController(ScreeningRepository screeningRepository, MovieRepository movieRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
    }

    @PostMapping
    public ResponseEntity<Screening> create(@PathVariable int movieId, @RequestBody Screening screening) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that ID was found.")
        );
        screening.setMovie(movie);
        screening.setCreatedAt(LocalDateTime.now());
        screening.setUpdatedAt(LocalDateTime.now());
        return new ResponseEntity<>(screeningRepository.save(screening), HttpStatus.CREATED);
    }

    // Works, but it's weird to have to add a movie id
    @GetMapping("/{s_id}")
    public ResponseEntity<Screening> getOne(@PathVariable(name="id") int id, @PathVariable(name="s_id") int s_id) {
        return ResponseEntity.ok(screeningRepository.findById(s_id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with that ID was found.")
        ));
    }

    @GetMapping
    public ResponseEntity<List<Screening>> getAll(@PathVariable(name="id") int movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that ID was found.")
        );

        // maybe findAll(...) can also filter
        return ResponseEntity.ok(screeningRepository.findAll().stream().filter(
                s -> s.getMovieId() == movie.getId()
        ).toList());
    }
}
