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
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<Screening> getAllScreenings(@PathVariable(name = "id") int id) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Movie Not Found"));
        return movie.getScreeningList();
    }

    @PostMapping
    public ResponseEntity<Screening> addScreening(@PathVariable(name = "id") int id, @RequestBody Screening screening) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Movie Not Found"));
        screening.setMovie(movie);
        screening.setCreatedAt(java.time.LocalDateTime.now());
        movie.getScreeningList().add(screening);
        return new ResponseEntity<>(this.screeningRepository.save(screening),HttpStatus.CREATED);
    }

//    @PostMapping
//    public ResponseEntity<Screening> addScreening(@RequestBody Screening screening) {
//        screening.setCreatedAt(java.time.LocalDateTime.now());
//        screening.
//    }
}
