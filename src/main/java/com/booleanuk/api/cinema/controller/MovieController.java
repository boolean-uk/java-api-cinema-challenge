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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public List<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    };

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        this.movieRepository.save(movie);
        List<Screening> screenings = movie.getScreenings();
        List<Screening> updatedScreenings = new ArrayList<>();
        if (screenings != null) {
            for (Screening screening : screenings) {
                Screening newScreening = new Screening(screening.getScreenNumber(), screening.getCapacity(), screening.getStartsAt(), movie);
                updatedScreenings.add(screeningRepository.save(newScreening));
            }
            movie.setScreenings(updatedScreenings);
        }
        return new ResponseEntity<Movie>(this.movieRepository.save(movie), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Cannot update this movie, as the movie id could not be found"));

        if(movie.getTitle() != null) movieToUpdate.setTitle(movie.getTitle());
        if(movie.getRating() != null) movieToUpdate.setRating(movie.getRating());
        if(movie.getDescription() != null) movieToUpdate.setDescription(movie.getDescription());
        if(movie.getRuntimeMins() != null) movieToUpdate.setRuntimeMins(movie.getRuntimeMins());

        return new ResponseEntity<Movie>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Cannot delete this movie, as the movie id could not be found"));
        this.movieRepository.delete(movieToDelete);
        return ResponseEntity.ok(movieToDelete);
    }
}
