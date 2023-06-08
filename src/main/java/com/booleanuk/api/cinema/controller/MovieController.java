package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/{movies}")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

//    @Autowired
//    private ScreeningRepository screeningRepository;

    @GetMapping
    public List<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable int id) {
        Movie movie = null;
        movie = this.movieRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with this id is not found!"));
        return ResponseEntity.ok(movie);
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        LocalDateTime createdAt = LocalDateTime.now();
        movie.setCreatedAt(createdAt);
        movie.setUpdatedAt(createdAt);
        return new ResponseEntity<Movie>(this.movieRepository.save(movie), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.movieRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not update movie as id is not found!"));

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());

//        movie.setId(id);
        LocalDateTime createdAt = LocalDateTime.now();
//        movie.setCreatedAt(movieToUpdate.getCreatedAt(createdAt));
        movieToUpdate.setUpdatedAt(LocalDateTime.now());
        return new ResponseEntity<Movie>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.movieRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not delete as id is not found!"));
        this.movieRepository.delete(movieToDelete);
        return ResponseEntity.ok(movieToDelete);
    }

//    public Movie createMovie(Movie movie) {
//        LocalDateTime createdAt = LocalDateTime.now();
//        movie.setCreatedAt(createdAt);
//        movie.setUpdatedAt(createdAt);
//        return movieRepository.save(movie);
//
//    }

//    @PostMapping("/{id}/screenings")
//    public ResponseEntity<Movie> createMovieScreening(@RequestBody Movie movie) {
//
//        return new ResponseEntity<Movie>(this.movieRepository.save(movie), HttpStatus.CREATED);
//    }

//    @GetMapping("/{id}/screenings")
//    public ResponseEntity<Movie> getMovieById(@PathVariable int id) {
//        Movie movie = null;
//        movie = this.movieRepository.findById(id).orElseThrow(() ->
//                new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with this id is not found!"));
//        return ResponseEntity.ok(movie);
//    }



}
