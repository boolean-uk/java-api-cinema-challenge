package com.booleanuk.api.cinema.screenings;

import java.time.LocalDateTime;
import java.util.List;
import com.booleanuk.api.cinema.movies.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<List<Screening>> getAllScreeningsOfMovie(@PathVariable int id) {
        Movie movie = movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find movie with this id"));
        return ResponseEntity.ok(movie.getScreenings());
    }

    @PostMapping
    public ResponseEntity<Screening> addScreening(@PathVariable int id, @RequestBody Screening screening) {
        Movie movie = movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find movie with this id"));
        movie.getScreenings().add(screening);
        screening.setMovie(movie);
        screening.setCreatedAt(LocalDateTime.now().toString());
        screening.setUpdatedAt(LocalDateTime.now().toString());

        return new ResponseEntity<Screening>(screeningRepository.save(screening), HttpStatus.CREATED);
    }
}
