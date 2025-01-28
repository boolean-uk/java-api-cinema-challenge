package com.booleanuk.api.cinema.movie;

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
        movie.setCreated_at(String.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(this.movieRepository.save(movie), HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
        public List<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie updatedMovie = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id"));

        updatedMovie.setTitle(movie.getTitle());
        updatedMovie.setRating(movie.getRating());
        updatedMovie.setDescription(movie.getDescription());
        updatedMovie.setRuntimeMins(movie.getRuntimeMins());
        updatedMovie.setUpdated_at(String.valueOf(LocalDateTime.now()));

        return new ResponseEntity<>(this.movieRepository.save(updatedMovie),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        Movie deletedMovie = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id"));
        this.movieRepository.delete(deletedMovie);
        return ResponseEntity.ok(deletedMovie);
    }
}
