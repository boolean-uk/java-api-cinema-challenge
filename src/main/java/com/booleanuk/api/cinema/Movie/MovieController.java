package com.booleanuk.api.cinema.Movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

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
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        // Logic to update a movie
        Movie existingMovie = movieRepository.findById(id).orElse(null);

        if (existingMovie == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setRating(movie.getRating());
        existingMovie.setDescription(movie.getDescription());
        existingMovie.setRuntimeMins(movie.getRuntimeMins());

        Movie updatedMovie = movieRepository.save(existingMovie);

        return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable Long id) {
        movieRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
