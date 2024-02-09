package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
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
        // TODO: If screenings included in request add them to screenings table with correct movie relationship
        movie.setCreatedAt(new Date());
        movie.setUpdatedAt(new Date());
        return new ResponseEntity<>(this.movieRepository.save(movie), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.findMovieById(id);
        // TODO: change checkHasRequiredFileds() to update only provided fields.
        this.checkHasRequiredFields(movie);
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        movieToUpdate.setUpdatedAt(new Date());
        return new ResponseEntity<>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        // TODO: On deletion, all connected screenings should be deleted.
        Movie movieToDelete = this.findMovieById(id);
        this.movieRepository.delete(movieToDelete);
        return ResponseEntity.ok(movieToDelete);
    }

    // Method used in updateMovie() and deleteMovie() to find a movie by the id
    private Movie findMovieById(int id) {
        return this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies with that id were found."));
    }

    // Method to check if all required fields are contained in the request, used in createMovie() and updateMovie()
    private void checkHasRequiredFields(Movie movie) {
        if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create a new movie, please check all required fields are correct.");
        }
    }
}
