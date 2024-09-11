package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/movies/{movieId}/screenings")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Screening> getAll() {
        return this.screeningRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Screening> create(@PathVariable int movieId, @RequestBody Screening body) {
        return this.movieRepository.findById(movieId)
                .map(movie -> {
                    body.setMovie(movie);
                    this.screeningRepository.save(body);
                    return new ResponseEntity<>(body, HttpStatus.CREATED);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie found for id: " + movieId));
    }
}
