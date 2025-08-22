package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class ScreeningController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @PostMapping("/{id}/screenings")
    public ResponseEntity<Screening> createScreening(@PathVariable int id, @RequestBody Screening screeningDetails) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with ID " + id + " not found.")
        );

        Screening screening = new Screening(
                screeningDetails.getScreenNumber(),
                screeningDetails.getCapacity(),
                screeningDetails.getStartsAt(),
                movie
        );
        screening.setCreatedAt(LocalDateTime.now());
        screening.setUpdatedAt(LocalDateTime.now());

        return new ResponseEntity<>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/screenings")
    public ResponseEntity<List<Screening>> getAllScreeningsForMovie(@PathVariable int id) {
        List<Screening> screenings = this.screeningRepository.findByMovieId(id);

        if (screenings.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No screenings found for Movie with ID " + id);
        }

        return ResponseEntity.ok(screenings);
    }
}
