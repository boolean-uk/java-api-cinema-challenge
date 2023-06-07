package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.DateTimeGenerator;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    public MovieController() {}

    @GetMapping
    public List<Movie> getAll() {
        return this.movieRepository.findAll();
    }

    @GetMapping("/{id}")
    public Movie getById(@PathVariable("id") Integer id) {
        return this.movieRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        movie.setCreatedAt(DateTimeGenerator.getDateTimeNow());
        movie.setUpdatedAt(movie.getCreatedAt());
        return new ResponseEntity<Movie>(this.movieRepository.save(movie), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Integer id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.movieRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setUpdatedAt(DateTimeGenerator.getDateTimeNow());

        return new ResponseEntity<Movie>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable Integer id) {
        Movie movieToDelete = this.movieRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        this.movieRepository.delete(movieToDelete);
        return ResponseEntity.ok(movieToDelete);
    }
}
