package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/movies")
public class ScreeningController {

    @Autowired
    ScreeningRepository screeningRepository;
    @Autowired
    MovieRepository movieRepository;

    @GetMapping("/{id}/screenings")
    public ResponseEntity<Screening> getScreeningsByMovieId(@PathVariable int id){
        Screening screening = screeningRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screenings for that movie were found"));
        return new ResponseEntity<>(screening, HttpStatus.OK);
    }
    @PostMapping("/{id}/screenings")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Screening> createScreening(@RequestBody Screening screening, @PathVariable int id) {
        screening.setMovie(movieRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id were found")));

        return new ResponseEntity<>(screeningRepository.save(screening), HttpStatus.CREATED);
    }



}
