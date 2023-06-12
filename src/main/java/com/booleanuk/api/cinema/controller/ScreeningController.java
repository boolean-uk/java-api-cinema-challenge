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

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/screenings")
    public List<Screening> getAllScreenings() {
        return this.screeningRepository.findAll();
    }
    @PostMapping("movies/{id}/screenings")
    public ResponseEntity<Screening> createMovieScreening(@RequestBody Screening screening, @PathVariable int id) {
        Movie tempMovie = this.movieRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't create screening as movie id not found"));
        screening.setMovie(tempMovie);
        LocalDateTime createdAt = LocalDateTime.now();
        screening.setCreatedAt(createdAt);
        screening.setUpdatedAt(createdAt);
        return new ResponseEntity<Screening>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }

    @GetMapping ("movies/{id}/screenings")
    public List<Screening> getAll(@PathVariable int movie_id) {
        List<Screening> screenings = this.screeningRepository.getScreeningByMovieId(movie_id);
        return screenings;
    }

}
