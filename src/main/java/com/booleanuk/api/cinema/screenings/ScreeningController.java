package com.booleanuk.api.cinema.screenings;

import com.booleanuk.api.cinema.movies.Movie;
import com.booleanuk.api.cinema.movies.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<List<Screening>> getAllScreenings(@PathVariable int id) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id found")
        );

        List<Screening> screenings = this.screeningRepository.findByMovie(movie);
        return ResponseEntity.ok(screenings);
    }

    @PostMapping
    public ResponseEntity<Screening> createNewScreening(@PathVariable int id, @RequestBody Screening screening) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id was found")
        );

        screening.setCreatedAt(LocalDateTime.now());
        screening.setUpdatedAt(LocalDateTime.now());
        screening.setMovie(movie);
        return new ResponseEntity<>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }

    @DeleteMapping("{screening_id}")
    public ResponseEntity<Screening> deleteScreening(@PathVariable int id) {
        Screening screeningToDelete = this.screeningRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with that id was found")
        );
        this.screeningRepository.delete(screeningToDelete);
        return ResponseEntity.ok(screeningToDelete);
    }

}