package com.booleanuk.api.controller;

import com.booleanuk.api.model.Movie;
import com.booleanuk.api.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        checkAllMovieFields(movie);
        Movie createdMovie = this.movieRepository.save(movie);
        createdMovie.setScreenings(new ArrayList<>());
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.getMovieWithNotFound(id);
        try {
            this.movieRepository.delete(movieToDelete);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Movie is a foreign key to a Screening");
        }
        movieToDelete.setScreenings(new ArrayList<>());
        return ResponseEntity.ok(movieToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        checkAllMovieFields(movie);
        Movie movieToUpdate = this.getMovieWithNotFound(id);
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRunTimeMins(movie.getRunTimeMins());
        return new ResponseEntity<>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

    //--------------------------- Private section---------------------------//

    private Movie getMovieWithNotFound(int id) {
        return this.movieRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No movie with that ID"));
    }

    private void checkAllMovieFields(Movie movie) {
        if (movie.getTitle().isEmpty() ||
        movie.getRating().isEmpty() ||
        movie.getDescription().isEmpty() ||
        movie.getRunTimeMins() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Bad data in RequestBody");
        }
    }
}
