package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.MovieListResponse;
import com.booleanuk.api.cinema.responses.MovieResponse;
import com.booleanuk.api.cinema.responses.Response;
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
    private MovieRepository movies;

    @GetMapping
    public ResponseEntity<MovieListResponse> getAll() {
        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.set(this.movies.findAll());
        return new ResponseEntity<>(movieListResponse, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable int id) {
        Movie toReturn = this.movies.findById(id)
                .orElse(
                        null
                );

        // 404 not found
        if (toReturn == null) {
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(toReturn);

        return new ResponseEntity<>(movieResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Movie toAdd) {
        toAdd.setCreatedAt(LocalDateTime.now());
        toAdd.setUpdatedAt(LocalDateTime.now());

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(this.movies.save(toAdd));

        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id) {
        Movie toDelete = this.movies.findById(id)
                .orElse(
                        null
                );

        // 404 not found
        if (toDelete == null) {
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        this.movies.delete(toDelete);

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(toDelete);

        return new ResponseEntity<>(movieResponse, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(@PathVariable(name = "id") int id, @RequestBody Movie newData) {
        Movie toUpdate = this.movies.findById(id)
                .orElse(
                        null
                );

        // 404 not found
        if (toUpdate == null) {
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        // 400 check all fields are correct
        if (newData.getDescription() == null || newData.getRating() == null ||
                newData.getTitle() == null || newData.getRuntimeMins() < 0) {
            ErrorResponse error = new ErrorResponse("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        toUpdate.setTitle(newData.getTitle());
        toUpdate.setDescription(newData.getDescription());
        toUpdate.setRating(newData.getRating());
        toUpdate.setRuntimeMins(newData.getRuntimeMins());
        toUpdate.setUpdatedAt(LocalDateTime.now());

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(this.movies.save(toUpdate));

        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

}
