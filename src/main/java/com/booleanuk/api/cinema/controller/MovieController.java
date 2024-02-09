package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    private LocalDateTime time = LocalDateTime.now();

    @GetMapping
    public List<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        movie.setCreatedAt(time);
        movie.setUpdatedAt(time);
        Movie newMovie = this.movieRepository.save(movie);
        newMovie.setScreenings(new ArrayList<>());
        return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
            movieToUpdate.setTitle(movie.getTitle());
            movieToUpdate.setRating(movie.getRating());
            movieToUpdate.setDescription(movie.getDescription());
            movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
            movieToUpdate.setUpdatedAt(LocalDateTime.now());
        return new ResponseEntity<>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        if(!movieToDelete.getScreenings().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movie has screenings, and could not be deleted");
        }
        this.movieRepository.delete(movieToDelete);
        movieToDelete.setScreenings(new ArrayList<>());
        return ResponseEntity.ok(movieToDelete);
    }

    @GetMapping("/{id}/screenings")
    public List<Screening> getAllScreenings(@PathVariable int id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie was not found"));
        return movie.getScreenings();
    }

    @PostMapping("/{id}/screenings")
    public ResponseEntity<Screening> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        screening.setCreatedAt(time);
        screening.setUpdatedAt(time);
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie was not found"));
        screening.setMovie(movie);
        return new ResponseEntity<>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }
}
