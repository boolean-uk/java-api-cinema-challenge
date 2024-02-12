package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;
    @GetMapping("movies/{id}/screenings")
    public ResponseEntity<?> getAllScreenings(@PathVariable int id){
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie == null) {
            return new ResponseEntity<>("Movie could not be found", HttpStatus.NOT_FOUND);
        }
        List<Screening> screening = movie.getScreenings();
        return ResponseEntity.ok(screening);
    }
    @PostMapping("movies/{id}/screenings")
    public ResponseEntity<?> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie == null) {
            return new ResponseEntity<>("Movie could not be found", HttpStatus.NOT_FOUND);
        }
        Screening screening1 = new Screening();
            screening1.setScreenNumber(screening.getScreenNumber());
            screening1.setCapacity(screening.getCapacity());
            System.out.println(screening.getStartsAt());
            screening1.setStartsAt(screening.getStartsAt());
            screening1.setMovie(this.movieRepository.findById(id).orElse(null));

        return new ResponseEntity<>(this.screeningRepository.save(screening1), HttpStatus.CREATED);
    }
}
