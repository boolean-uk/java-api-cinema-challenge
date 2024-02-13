package com.booleanuk.api.cinema.core.controller;

import com.booleanuk.api.cinema.core.model.Movie;
import com.booleanuk.api.cinema.core.model.Screening;
import com.booleanuk.api.cinema.core.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") int id) {
        Movie movie = movieRepository
                .findById((long) id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(movie);
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        checkIfMovieIsValid(movie);
        Movie newMovie = movieRepository.save(movie);
        return ResponseEntity.ok(newMovie);
    }

    @PutMapping("{id}")
    public Movie updateMovie(@PathVariable("id") int id, @RequestBody Movie movie) {
        checkIfMovieIsValid(movie);
        Movie movieToUpdate = movieRepository
                .findById((long) id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        if (movieToUpdate != null) {
            movieToUpdate.setTitle(movie.getTitle());
            movieToUpdate.setRating(movie.getRating());
            movieToUpdate.setDescription(movie.getDescription());
            movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
            movieRepository.save(movieToUpdate);
        }
        return movieToUpdate;
    }

    @DeleteMapping("{id}")
    public Movie deleteMovie(@PathVariable("id") int id) {
        Movie movieToDelete = movieRepository
                .findById((long) id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        if (movieToDelete != null) {
            movieRepository.delete(movieToDelete);
            movieToDelete.setScreenings(new ArrayList<>(movieToDelete.getScreenings()));
        }
        return movieToDelete;
    }

    private void checkIfMovieIsValid(Movie movie) {
        if (movie.getTitle() == null || movie.getTitle().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is required");
        } else if (movie.getRating() == null || movie.getRating().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating is required");
        } else if (movie.getDescription() == null || movie.getDescription().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Description is required");
        } else if (movie.getRuntimeMins() == null || movie.getRuntimeMins() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Runtime is required");
        }
    }
}
