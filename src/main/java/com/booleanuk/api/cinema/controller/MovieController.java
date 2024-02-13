package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Response> createMovie(@RequestBody Movie movie) {
        // 400 Bad request if a field not present
        if (movie.getTitle() == null || movie.getRating() == null ||
                movie.getDescription() == null || movie.getRuntimeMins() <= 0) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a new movie, please check all fields are correct.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        movie.setCreatedAt(ZonedDateTime.now());
        movie.setUpdatedAt(ZonedDateTime.now());

        // Create screenings
        Movie saved = this.movieRepository.save(movie);
        if(movie.getScreenings() != null) {
            for (Screening screening : movie.getScreenings()) {
                // 400 Bad request if not all fields present in screening
                if (screening.getScreenNumber() <= 0 || screening.getCapacity() <= 0 || screening.getStartsAt() == null) {
                    ErrorResponse error = new ErrorResponse();
                    error.set("Could not create a screening for the specified movie, " +
                            "please check all required fields are correct.");
                    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
                }
                screening.setMovie(saved);
                screening.setCreatedAt(ZonedDateTime.now());
                screening.setUpdatedAt(ZonedDateTime.now());
                this.screeningRepository.save(screening);
            }
        }
        else {
            movie.setScreenings(new ArrayList<>());
        }

        // Response with the created movie
        SuccessResponse response = new SuccessResponse();
        response.set(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response> getAllMovies() {
        List<Movie> movies = this.movieRepository.findAll();
        // Response with list of movies
        SuccessResponse response = new SuccessResponse();
        response.set(movies);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        // 404 Not found if no movie with given ID
        Movie movieToUpdate = this.getMovieById(id);
        if (movieToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        // 400 Bad request if no fields are present in the put request
        if (movie.getTitle() == null && movie.getRating() == null &&
                movie.getDescription() == null && movie.getRuntimeMins() <= 0) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not update movie, please check all required fields are correct.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        // Update field only if present
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

        // Update updatedAt
        movieToUpdate.setUpdatedAt(ZonedDateTime.now());

        // Response with the updated movie
        SuccessResponse response = new SuccessResponse();
        response.set(this.movieRepository.save(movieToUpdate));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteMovie(@PathVariable int id) {
        // 404 Not found if no movie with given ID
        Movie movieToDelete = this.getMovieById(id);
        if (movieToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        // Delete all related screenings
        for (Screening screening : movieToDelete.getScreenings()) {
            screeningRepository.delete(screening);
        }
        this.movieRepository.delete(movieToDelete);

        // Response with deleted movie
        SuccessResponse response = new SuccessResponse();
        response.set(movieToDelete);
        return ResponseEntity.ok(response);
    }

    // Method used in updateMovie() and deleteMovie() to find a movie by the id
    private Movie getMovieById(int id) {
        return this.movieRepository.findById(id).orElse(null);
    }

}
