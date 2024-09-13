package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.Movie;
import com.booleanuk.api.cinema.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies/{movieId}/screenings")
public class ScreeningController {
    @Autowired
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;

    public ScreeningController(ScreeningRepository screeningRepository, MovieRepository movieRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public List<Screening> getAllScreenings() {
        return this.screeningRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Screening> createScreening(@PathVariable int movieId, @RequestBody Screening screening) {

        Movie movie = this.movieRepository.findById(movieId).orElseThrow(
                () -> new RuntimeException("No movie with that ID found")
        );
        screening.setMovie(movie);
        return new ResponseEntity<>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Screening> getScreeningById(@PathVariable int id) {
        Screening screening = null;
        screening = this.screeningRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No screening with that ID found")
        );
        return ResponseEntity.ok(screening);
    }

    @PutMapping()
    public ResponseEntity<Screening> updateScreening(@PathVariable int id, @RequestBody Screening screening) {
        Screening screeningToUpdate = this.screeningRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No screening with that ID found")
        );
        screening.setId(screeningToUpdate.getId());
        return new ResponseEntity<>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<Screening> deleteScreening(@RequestBody int id) {
        Screening screeningToDelete = this.screeningRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No screening with that ID found")
        );
        this.screeningRepository.delete(screeningToDelete);
        return ResponseEntity.ok(screeningToDelete);
    }
}
