package com.booleanuk.api.cinema.controller;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Movie;
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
@RequestMapping("movies")
public class ScreeningController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    public ScreeningRepository screeningRepository;
    @GetMapping("/{id}/screenings")
    public List<Screening> getAllScreenings() {return this.screeningRepository.findAll(); }
    @PostMapping("/{id}/screenings")
    public ResponseEntity<Screening> createScreening(@RequestBody Screening screening, @PathVariable int id) {
        Movie tempMovie = this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that ID'"));
        screening.setMovie(tempMovie);
        screening.setCreatedAt(LocalDateTime.now());
        return ResponseEntity.ok(this.screeningRepository.save(screening));
    }
}
