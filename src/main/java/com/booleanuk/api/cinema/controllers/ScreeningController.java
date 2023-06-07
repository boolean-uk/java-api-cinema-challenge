package com.booleanuk.api.cinema.controllers;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("movies/{movieId}/screenings")
public class ScreeningController {
    @Autowired
    ScreeningRepository screeningRepository;
    @Autowired
    MovieRepository movieRepository;

    @GetMapping
    public List<Screening> getAll(@PathVariable int movieId) {
        return this.screeningRepository.findByMovieId(movieId);
    }

    @PostMapping
    public ResponseEntity<Screening> createScreening(@PathVariable int movieId, @RequestBody Screening screening){
        Movie movie = null;
        movie = this.movieRepository.findById(movieId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with provided id found."));
        screening.setMovie(movie);
        return new ResponseEntity<>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }
}