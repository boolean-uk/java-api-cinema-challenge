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

import java.util.List;

@RestController
@RequestMapping("/movies/{id}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<Screening> getAllScreenings(@PathVariable int id) {
        return this.screeningRepository.getScreeningsByMovieId(id);
    };

    @PostMapping
    public ResponseEntity<Screening> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        Movie movie = movieRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot create this screening, as the movie id could not be found"));
        screening.setMovie(movie);
        return new ResponseEntity<Screening>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }
}
