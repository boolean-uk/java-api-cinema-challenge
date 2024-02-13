package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie createdMovie = movieRepository.save(movie);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            Movie existingMovie = optionalMovie.get();
            existingMovie.setTitle(movie.getTitle());
            existingMovie.setRating(movie.getRating());
            existingMovie.setDescription(movie.getDescription());
            existingMovie.setRuntimeMins(movie.getRuntimeMins());
            Movie updatedMovie = movieRepository.save(existingMovie);
            return new ResponseEntity<>(updatedMovie, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            Movie deletedMovie = optionalMovie.get();
            movieRepository.delete(deletedMovie);
            return new ResponseEntity<>(deletedMovie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
