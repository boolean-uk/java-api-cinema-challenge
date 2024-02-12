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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie) {
        if (movie.getScreenings() != null && (!movie.getScreenings().isEmpty())) {
            for(Screening screening : movie.getScreenings()) {
                screening.setMovie(movie);
            }
        } else {
            movie.setScreenings(new ArrayList<Screening>());
        }
        movie.setCreatedAt(String.valueOf(LocalDateTime.now()));
        movie.setUpdatedAt(movie.getCreatedAt());
        Movie createdMovie = this.movieRepository.save(movie);
        return new ResponseEntity<Movie>(createdMovie, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Movie> getAll() {
        return this.movieRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getById(@PathVariable int id) {
        Movie movie = null;
        movie = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        movieToUpdate.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME));

        return new ResponseEntity<Movie>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> delete(@PathVariable int id) {
        Movie movieToDelete = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.movieRepository.delete(movieToDelete);
        movieToDelete.setScreenings(new ArrayList<Screening>());
        return ResponseEntity.ok(movieToDelete);
    }

}
