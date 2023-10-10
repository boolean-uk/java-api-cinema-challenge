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
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("movies/{id}/screenings")
    public List<Screening> getScreenings(@PathVariable int id) {
        return this.screeningRepository.getScreeningByMovieId(id);
    }

    @PostMapping("/movies/{id}/screenings")
    public ResponseEntity<Screening> createScreening(@RequestBody Screening screening, @PathVariable int id) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry movie with this id does not exist, can't create a screening"));
        screening.setMovie(movie);
        screening.setScreenNumber(screening.getScreenNumber());
        screening.setCapacity(screening.getCapacity());
        screening.setStartsAt(screening.getStartsAt());
        return new ResponseEntity<>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }
}

