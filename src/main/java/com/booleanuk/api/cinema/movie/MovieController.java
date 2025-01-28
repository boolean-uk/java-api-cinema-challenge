package com.booleanuk.api.cinema.movie;

import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.MovieResponse;
import com.booleanuk.api.cinema.response.MovieResponseList;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @PostMapping
    public ResponseEntity<Response<?>> createMovie(@RequestBody Movie movie) {
        MovieResponse response = new MovieResponse();
        movie.setCreated_at(String.valueOf(LocalDateTime.now()));

        try {
            response.set(this.movieRepository.save(movie));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MovieResponseList> getAllMovies() {
        MovieResponseList response = new MovieResponseList();
        response.set(this.movieRepository.findAll());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie updatedMovie = null;
        MovieResponse response = new MovieResponse();


        try {
            updatedMovie = this.movieRepository.findById(id).orElse(null);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        if (updatedMovie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        updatedMovie.setTitle(movie.getTitle());
        updatedMovie.setRating(movie.getRating());
        updatedMovie.setDescription(movie.getDescription());
        updatedMovie.setRuntimeMins(movie.getRuntimeMins());
        updatedMovie.setUpdated_at(String.valueOf(LocalDateTime.now()));

        response.set(this.movieRepository.save(updatedMovie));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteMovie(@PathVariable int id) {
        Movie deletedMovie = this.movieRepository.findById(id).orElse(null);
        MovieResponse response = new MovieResponse();

        if(deletedMovie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        this.movieRepository.delete(deletedMovie);
        response.set(deletedMovie);
        return ResponseEntity.ok(response);
    }
}
