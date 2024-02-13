package com.booleanuk.api.cinema.controller;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

//Imports skipped
@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<Movie> getALlMovies() {
        return this.movieRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        movie.setCreatedAt(LocalDateTime.now());
        return new ResponseEntity<>(this.movieRepository.save(movie), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable int id) {
        return ResponseEntity.ok(this.getAMovie(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.getAMovie(id);
        this.movieRepository.delete(movieToDelete);
        return ResponseEntity.ok(movieToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.getAMovie(id);
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        movieToUpdate.setUpdatedAt(LocalDateTime.now());
        return new ResponseEntity<>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

    private Movie getAMovie(int id) {
        return this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
    }
}

