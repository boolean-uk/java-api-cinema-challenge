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

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        try {
            movie.setCreatedAt(LocalDateTime.now());
            movie.setUpdatedAt(LocalDateTime.now());
            return new ResponseEntity<>(this.movieRepository.save(movie), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not save the movie: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(this.movieRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getOneMovie(@PathVariable int id) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with ID " + id + " not found.")
        );
        return ResponseEntity.ok(movie);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Movie updateMovie(@PathVariable int id, @RequestBody Movie updatedMovie) {
        return this.movieRepository.findById(id)
                .map(movie -> {
                    movie.setTitle(updatedMovie.getTitle());
                    movie.setRating(updatedMovie.getRating());
                    movie.setDescription(updatedMovie.getDescription());
                    movie.setRuntimeMins(updatedMovie.getRuntimeMins());
                    movie.setUpdatedAt(LocalDateTime.now());
                    return this.movieRepository.save(movie);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with ID " + id + " not found."));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with ID " + id + " not found.")
        );
        this.movieRepository.delete(movieToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
