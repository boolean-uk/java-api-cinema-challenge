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
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository repo;

    @Autowired
    private MovieRepository movies;

    @PostMapping
    public ResponseEntity<Screening> create(@PathVariable int id, @RequestBody Screening screening){
        Movie tempMovie = movies
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with this ID."));

        screening.setMovie(tempMovie);
        screening.setCreatedAt(LocalDateTime.now());
        screening.setUpdatedAt(LocalDateTime.now());

        return new ResponseEntity<Screening>(repo.save(screening), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Screening>> getScreeningsForMovie(@PathVariable int id) {
        Movie movie = movies
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Screening> screenings = movie.getScreenings();

        return ResponseEntity.ok(screenings);
    }
}
