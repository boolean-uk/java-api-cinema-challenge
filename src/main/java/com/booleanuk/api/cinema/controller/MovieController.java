package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.payload.response.ErrorResponse;
import com.booleanuk.api.cinema.payload.response.MovieListResponse;
import com.booleanuk.api.cinema.payload.response.MovieResponse;
import com.booleanuk.api.cinema.payload.response.Response;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {


    @Autowired
    private MovieRepository repository;

    @GetMapping
    @JsonView(Views.Detailed.class)
    public ResponseEntity<MovieListResponse> getAll() {
        MovieListResponse response = new MovieListResponse(this.repository.findAll());
        return ResponseEntity.ok(response);
    }


    @PostMapping
    @JsonView(Views.Detailed.class)
    public ResponseEntity<Response<?>> PostUser( @RequestBody Movie movie) {
        MovieResponse response = new MovieResponse();
        for (Screening screening: movie.getScreenings()) {
            screening.setMovie(movie);
        }
        try {
            response.set(this.repository.save(movie));
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Bad request"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @JsonView(Views.Detailed.class)
    public ResponseEntity<Response<?>> create(@PathVariable int id,  @RequestBody Movie movie) {
        Movie movieToUpdate = this.repository.findById(id).orElse(null);
        if (movieToUpdate == null)
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);

        BeanUtils.copyProperties(movie, movieToUpdate, "id", "createdAt", "screenings");
        try {
            movieToUpdate = this.repository.save(movieToUpdate);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Bad request"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new MovieResponse(movieToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @JsonView(Views.Detailed.class)
    public ResponseEntity<Response<?>> delete( @PathVariable int id) {
        Movie movieToUpdate = this.repository.findById(id).orElse(null);
        if (movieToUpdate == null)
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);

        this.repository.delete(movieToUpdate);
        return ResponseEntity.ok(new MovieResponse(movieToUpdate));
    }
}
