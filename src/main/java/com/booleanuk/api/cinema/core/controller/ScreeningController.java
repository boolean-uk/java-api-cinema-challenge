package com.booleanuk.api.cinema.core.controller;

import com.booleanuk.api.cinema.core.model.Movie;
import com.booleanuk.api.cinema.core.model.Screening;
import com.booleanuk.api.cinema.core.repository.MovieRepository;
import com.booleanuk.api.cinema.core.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/movies")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/{id}/screenings")
    public ResponseEntity<Iterable<Screening>> getScreeningsByMovieId(@PathVariable("id") Long id) {
        Movie movie = movieRepository
                .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        return ResponseEntity.ok(movie.getScreenings());
    }

    @PostMapping("/{id}/screenings")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Screening> createScreening(@PathVariable("id") Long id, @RequestBody Screening screening) {
        Movie movie = movieRepository
                .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        screening.setMovie(movie);

        checkIfScreeningIsValid(screening);
        Screening newScreening = screeningRepository.save(screening);
        return ResponseEntity.ok(newScreening);
    }

    private void checkIfScreeningIsValid(Screening screening) {
        if (screening.getMovie() == null) {
            throw new IllegalArgumentException("Screening must have a movie");
        } else if (screening.getScreenNumber() == null || screening.getScreenNumber() <= 0){
            throw new IllegalArgumentException("The screen must have a number");
        } else if (screening.getCapacity() == null || screening.getCapacity() <= 0) {
            throw new IllegalArgumentException("Screening must have a capacity");
        }
    }
}
