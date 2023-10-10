package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ScreeningController {
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;

    public ScreeningController(ScreeningRepository screeningRepository, MovieRepository movieRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
    }

    @GetMapping("movies/{id}/screenings")
    public ResponseEntity<Iterable<Screening>> findAll(@PathVariable long id) {
        return ResponseEntity.ok(this.screeningRepository.findScreeningByMovie_Id(id));
    }

    @PostMapping("movies/{id}/screenings")
    public ResponseEntity<Screening> createScreening(@PathVariable long id, @RequestBody Screening screening) {
        Optional<Movie> movieOptional = this.movieRepository.findById(id);
        if (movieOptional.isEmpty()) return ResponseEntity.notFound().build();
        screening.setMovie(movieOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.screeningRepository.save(screening));
    }
}
