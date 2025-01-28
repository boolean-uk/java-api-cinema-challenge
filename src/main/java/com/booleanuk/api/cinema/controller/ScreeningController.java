package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/screenings")
public class ScreeningController {
    ScreeningRepository screeningRepository;
    MovieRepository movieRepository;

    public ScreeningController(ScreeningRepository screeningRepository, MovieRepository movieRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screening> getOne(@PathVariable(name="id") int id) {
        return ResponseEntity.ok(screeningRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with that ID was found.")
        ));
    }
}
