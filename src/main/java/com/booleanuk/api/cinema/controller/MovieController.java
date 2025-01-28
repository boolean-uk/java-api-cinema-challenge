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
@RequestMapping("/movies")
public class MovieController {
    private MovieRepository movieRepository;
    private ScreeningRepository screeningRepository;

    public MovieController(MovieRepository movieRepository, ScreeningRepository screeningRepository) {
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getOne(@PathVariable int id) {
        return ResponseEntity.ok(movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that ID was found.")
        ));
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAll() {
        return ResponseEntity.ok(movieRepository.findAll());
    }

    @PostMapping("{id}/screenings")
    public ResponseEntity<Screening> create(@PathVariable(name="id") int movieId, @RequestBody Screening screening) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that ID was found.")
        );
        screening.setMovie(movie);
        screening.setCreatedAt(LocalDateTime.now());
        screening.setUpdatedAt(LocalDateTime.now());
        return new ResponseEntity<>(screeningRepository.save(screening), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/screenings")
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
