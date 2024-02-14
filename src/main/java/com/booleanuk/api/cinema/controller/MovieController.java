package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return ResponseEntity.ok(new Response<>("Success", movies));
    }

    @PostMapping
    public ResponseEntity<Response<?>> createMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        return new ResponseEntity<>(new Response<>("Movie created successfully", savedMovie), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        Movie existingMovie = movieRepository.findById(Math.toIntExact(id)).orElse(null);
        if (existingMovie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>("Movie not found", null));
        } else {
            movie.setId(Math.toIntExact(id));
            Movie updatedMovie = movieRepository.save(movie);
            return ResponseEntity.ok(new Response<>("Movie updated successfully", updatedMovie));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteMovie(@PathVariable Long id) {
        Movie existingMovie = movieRepository.findById(Math.toIntExact(id)).orElse(null);
        if (existingMovie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>("Movie not found", null));
        } else {
            movieRepository.delete(existingMovie);
            return ResponseEntity.ok(new Response<>("Movie deleted successfully", existingMovie));
        }
    }
}
