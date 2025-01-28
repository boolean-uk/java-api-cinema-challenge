package com.booleanuk.api.cinema.screening;

import com.booleanuk.api.cinema.movie.Movie;
import com.booleanuk.api.cinema.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @PostMapping("movies/{id}/screenings")
    public ResponseEntity<Screening> createScreening(@RequestBody Screening screening, @PathVariable int id) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id"));
        screening.setMovie(movie);
        screening.setCreated_at(String.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }

    @GetMapping("movies/{id}/screenings")
    @ResponseStatus(HttpStatus.OK)
    public List<Screening> getAllScreenings(@PathVariable int id) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No movie with that id"));
        return movie.getScreenings();
    }

    @DeleteMapping("screenings/{id}")
    public ResponseEntity<Screening> deleteScreening(@PathVariable int id) {
        Screening deletedScreening = this.screeningRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No screening with that id"));
        this.screeningRepository.delete(deletedScreening);
        return ResponseEntity.ok(deletedScreening);
    }
}
