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
@RequestMapping("movies/{id}/screenings") // id = id of the movie
public class ScreeningController {

    @Autowired
    private ScreeningRepository screenings;

    @Autowired
    private MovieRepository movies;

    @PostMapping
    public ResponseEntity<Screening> create(@PathVariable(name = "id") int id, @RequestBody Screening toAdd) {
        Movie movie = this.movies.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Given movie not found.")
                );

        toAdd.setMovie(movie);
        toAdd.setCreatedAt(LocalDateTime.now());
        toAdd.setUpdatedAt(LocalDateTime.now());
        movie.addScreening(toAdd);

        return new ResponseEntity<>(this.screenings.save(toAdd), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Screening>> getOne(@PathVariable(name = "id") int id) {
        Movie movie = this.movies.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Given movie not found.")
                );

        return new ResponseEntity<>(movie.getScreenings(), HttpStatus.OK);
    }

}
