package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies/{id}/screenings")
public class ScreeningController {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public ScreeningController(ScreeningRepository screeningRepository, MovieRepository movieRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
    }

    @PostMapping
    public ResponseEntity<Screening> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            screening.setMovie(movie);
            screening.setCreatedAt(LocalDateTime.now());
            screening.setUpdatedAt(LocalDateTime.now());
            Screening createdScreening = screeningRepository.save(screening);
            return new ResponseEntity<>(createdScreening, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Screening>> getAllScreenings(@PathVariable int id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            List<Screening> screenings = movie.getScreenings();
            return new ResponseEntity<>(screenings, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
