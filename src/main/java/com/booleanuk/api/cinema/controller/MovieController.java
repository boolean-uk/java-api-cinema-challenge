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

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        this.checkHasRequiredFields(movie);
        movie.setCreatedAt(ZonedDateTime.now());
        movie.setUpdatedAt(ZonedDateTime.now());
        Movie saved = this.movieRepository.save(movie);
        if(movie.getScreenings() != null) {
            for (Screening screening : movie.getScreenings()) {
                checkScreeningHasRequiredFields(screening);
                screening.setMovie(saved);
                screening.setCreatedAt(ZonedDateTime.now());
                screening.setUpdatedAt(ZonedDateTime.now());
                this.screeningRepository.save(screening);
            }
        }
        else {
            movie.setScreenings(new ArrayList<>());
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.findMovieById(id);
        if (movie.getTitle() != null) {
            movieToUpdate.setTitle(movie.getTitle());
        }
        if (movie.getRating() != null) {
            movieToUpdate.setRating(movie.getRating());
        }
        if (movie.getDescription() != null) {
            movieToUpdate.setDescription(movie.getDescription());
        }
        if (movie.getRuntimeMins() > 0) {
            movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        }
        movieToUpdate.setUpdatedAt(ZonedDateTime.now());
        return new ResponseEntity<>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.findMovieById(id);
        for (Screening screening : movieToDelete.getScreenings()) {
            screeningRepository.delete(screening);
        }
        this.movieRepository.delete(movieToDelete);
        return ResponseEntity.ok(movieToDelete);
    }

    // Method used in updateMovie() and deleteMovie() to find a movie by the id
    private Movie findMovieById(int id) {
        return this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies with that id were found."));
    }

    // Method to check if all required fields are contained in the createMovie request, used in createMovie()
    private void checkHasRequiredFields(Movie movie) {
        if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create a new movie, please check all required fields are correct.");
        }
    }

    // Method to check if all required fields are contained in screening
    private void checkScreeningHasRequiredFields(Screening screening) {
        if (screening.getScreenNumber() <= 0 || screening.getCapacity() <= 0 || screening.getStartsAt() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create a screening for the specified movie, please check all required fields are correct.");
        }
    }
}
