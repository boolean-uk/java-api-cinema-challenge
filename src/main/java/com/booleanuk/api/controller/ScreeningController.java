package com.booleanuk.api.controller;

import com.booleanuk.api.model.Movie;
import com.booleanuk.api.model.Screening;
import com.booleanuk.api.repository.MovieRepository;
import com.booleanuk.api.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/screenings")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Screening> getById(@PathVariable int id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening not found"));
        return ResponseEntity.ok(screening);
    }

    @GetMapping
    public List<Screening> getAll() {
        return screeningRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Screening> create(@RequestBody Screening screening) {
        Movie movie = movieRepository.findById(screening.getMovie().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        screening.setMovie(movie);
        Screening savedScreening = screeningRepository.save(screening);
        return new ResponseEntity<>(savedScreening, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Screening> delete(@PathVariable int id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening not found"));
        screeningRepository.delete(screening);
        return ResponseEntity.ok(screening);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Screening> update(@PathVariable int id, @RequestBody Screening screeningDetails) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening not found"));
        Movie movie = movieRepository.findById(screeningDetails.getMovie().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        screening.setMovie(movie);
        Screening updatedScreening = screeningRepository.save(screening);
        return ResponseEntity.ok(updatedScreening);
    }
}
