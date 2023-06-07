package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public List<Movie> getAllMovies(){
        return this.movieRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable int id){
        Movie movie = this.movieRepository.findById(id).orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND,"Movie not found!"));
        return ResponseEntity.ok(movie);
    }
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        movie.setCreatedAt(LocalDateTime.now());
        Movie savedMovie = movieRepository.save(movie);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movieDetails) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        return optionalMovie.map(movie -> {
            movie.setTitle(movieDetails.getTitle());
            movie.setDescription(movieDetails.getDescription());
            movie.setRating(movieDetails.getRating());
            movie.setRuntimeMins(movieDetails.getRuntimeMins());

            movie.setUpdatedAt(LocalDateTime.now());
            Movie updatedMovie = movieRepository.save(movie);
            return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable int id) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }

        // Delete associated screenings
        List<Screening> screenings = screeningRepository.findByMovieId(id);
        for (Screening screening : screenings) {
            screeningRepository.delete(screening);
        }

        // Delete the movie
        movieRepository.delete(movie);

        return ResponseEntity.noContent().build();
    }
}
