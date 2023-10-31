package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.Model.Movie;
import com.booleanuk.api.cinema.Model.Screening;
import com.booleanuk.api.cinema.Repository.MovieRepository;
import com.booleanuk.api.cinema.Repository.ScreeningRepository;
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

    @GetMapping("/movies/{movieId}/screenings")
    public List<Screening> getAllScreenings(@PathVariable int movieId) {
        return this.screeningRepository.findByMovieId(movieId);
    }

    @PostMapping("/movies/{movieId}/screenings")
    public ResponseEntity<Screening> createScreening(@PathVariable int movieId, @RequestBody Screening screening) {
        Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies matching that id were found"));
        screening.setMovie(movie);
        return new ResponseEntity<Screening>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }
}