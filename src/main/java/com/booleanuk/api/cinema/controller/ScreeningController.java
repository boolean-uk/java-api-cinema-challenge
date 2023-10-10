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
public class ScreeningController {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ScreeningRepository screeningRepository;

    @GetMapping("/movies/{id}/screenings")
    public List<Screening> getAllScreenings(@PathVariable int movieId){
        return this.screeningRepository.findByMovieId(movieId);
    }

    @PostMapping("/movies/{id}/screenings")
    public ResponseEntity<Screening> createScreening(@PathVariable int movieId, @RequestBody Screening screening){
        Movie movie = this.movieRepository.findById(movieId).orElseThrow( () -> new ResponseStatusException((HttpStatus.NOT_FOUND), "Movie Id not found"));
        screening.setMovie(movie);
        return new ResponseEntity<Screening>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }


}
