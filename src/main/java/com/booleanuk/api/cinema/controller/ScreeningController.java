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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;


    @GetMapping("/{movie_id}/screenings")
    public ResponseEntity<List<Screening>> getAllScreenings(@PathVariable int movie_id) {
        Movie movie = this.movieRepository.findById(movie_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));

        return ResponseEntity.ok(movie.getScreenings());
    }


    @PostMapping("/{movie_id}/screenings")
    public ResponseEntity<Screening> createScreening(@PathVariable int movie_id, @RequestBody Screening screening) {
        Movie tempMov = this.movieRepository.findById(screening.getMovie().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie found"));
        screening.setMovie(tempMov);

        return ResponseEntity.ok(this.screeningRepository.save(screening));
    }


}
