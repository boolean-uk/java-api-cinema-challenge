package com.booleanuk.api.cinema.movie;

import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.MovieListResponse;
import com.booleanuk.api.cinema.response.MovieResponse;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<MovieListResponse> getAllMovies() {
        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.set(this.movieRepository.findAll());
        return ResponseEntity.ok(movieListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createMovie(@RequestBody Movie movie) {
        MovieResponse movieResponse = new MovieResponse();

        if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() == 0) {
            ErrorResponse error = new ErrorResponse();
            error.set("Some fields are invalid");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

        } else {
            Date today = new Date();
            movie.setCreatedAt(today);
            movie.setUpdatedAt(today);

            movieResponse.set(this.movieRepository.save(movie));
        }
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        MovieResponse movieResponse = new MovieResponse();

        if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() == 0) {
            ErrorResponse error = new ErrorResponse();
            error.set("Some fields are invalid");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

        } else {
            Movie movieToUpdate = this.movieRepository.findById(id).orElse(null);

            if (movieToUpdate == null) {
                ErrorResponse error = new ErrorResponse();
                error.set("Not found to update");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

            } else {
                movieToUpdate.setTitle(movie.getTitle());
                movieToUpdate.setRating(movie.getRating());
                movieToUpdate.setDescription(movie.getDescription());
                movieToUpdate.setRuntimeMins(movie.getRuntimeMins());

                Date today = new Date();
                movieToUpdate.setUpdatedAt(today);
                movieResponse.set(this.movieRepository.save(movieToUpdate));
            }
            return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteMovie(@PathVariable int id) {
        MovieResponse movieResponse = new MovieResponse();
        Movie movieToDelete = this.movieRepository.findById(id).orElse(null);

        if (movieToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Not found to delete");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.movieRepository.delete(movieToDelete);
        movieResponse.set(movieToDelete);

        return ResponseEntity.ok(movieResponse);
    }
}
