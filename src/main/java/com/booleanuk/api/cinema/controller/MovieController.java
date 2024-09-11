package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
public class MovieController {
    MovieRepository movieRepository;

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) throws ResponseStatusException {
        try {
            return new ResponseEntity<>(this.movieRepository.save(movie), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An error occurred when attempting to create a movie: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() throws ResponseStatusException {
        return ResponseEntity.ok(this.movieRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable (name = "id") int id) throws ResponseStatusException {
        return ResponseEntity.ok(findMovieById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable (name = "id") int id, @RequestBody Movie movie) throws ResponseStatusException {
        Movie movieToUpdate = findMovieById(id);
        try {
            movieToUpdate.setTitle(movie.getTitle());
            movieToUpdate.setRating(movie.getRating());
            movieToUpdate.setDescription(movie.getDescription());
            movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
            movieToUpdate.setUpdatedAt(LocalDateTime.now());
            return new ResponseEntity<>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An error occurred when attempting to update movie: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable (name = "id") int id) throws ResponseStatusException {
        Movie movieToDelete = findMovieById(id);
        this.movieRepository.delete(movieToDelete);
        return ResponseEntity.ok(movieToDelete);
    }

    private Movie findMovieById(int id) throws ResponseStatusException {
        return this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with the provided ID does not exist."));
    }
}
