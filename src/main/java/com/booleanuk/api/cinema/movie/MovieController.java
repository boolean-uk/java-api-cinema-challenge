package com.booleanuk.api.cinema.movie;

import com.booleanuk.api.cinema.screening.Screening;
import lombok.Getter;
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

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        validateMovieOrThrowException(movie);

        Movie newMovie = this.movieRepository.save(movie);

        return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(this.movieRepository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        validateMovieOrThrowException(movie);

        Movie movieToUpdate = findMovieOrThrowException(id);

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRunTimeMins(movie.getRunTimeMins());
        if(movie.getScreenings() == null) {
            movieToUpdate.setScreenings(new ArrayList<Screening>());    // If no screenings array is provided, the movie should be created as normal.
        }
        else {
            movieToUpdate.setScreenings(movie.getScreenings());
        }

        this.movieRepository.save(movieToUpdate);

        return new ResponseEntity<>(movieToUpdate, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        Movie movieToBeDeleted = findMovieOrThrowException(id);

        this.movieRepository.deleteById(id);

        return ResponseEntity.ok(movieToBeDeleted);
    }

    private void validateMovieOrThrowException(Movie movie) {
        if(movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRunTimeMins() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create movie, please check all fields are correct.");
        }
    }

    private Movie findMovieOrThrowException(int id) {
        return this.movieRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id found."));
    }
}
