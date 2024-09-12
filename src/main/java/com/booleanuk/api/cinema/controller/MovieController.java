package com.booleanuk.api.cinema.controller;

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
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<List<Movie>> getMovies() {
        return ResponseEntity.ok(this.movieRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        return new ResponseEntity<>(this.movieRepository.save(movie), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movieDetails) {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        existingMovie.setTitle(movieDetails.getTitle());
        existingMovie.setRating(movieDetails.getRating());
        existingMovie.setDescription(movieDetails.getDescription());
        existingMovie.setRuntimeMins(movieDetails.getRuntimeMins());
        Movie updatedMovie = movieRepository.save(existingMovie);
        return ResponseEntity.ok(updatedMovie);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        Movie movie = this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));

        this.movieRepository.delete(movie);
        return ResponseEntity.ok(movie);
    }
}
