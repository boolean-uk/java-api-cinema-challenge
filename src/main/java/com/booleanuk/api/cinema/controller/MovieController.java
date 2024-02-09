package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.dto.CustomerDto;
import com.booleanuk.api.cinema.dto.MovieDto;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    MovieRepository repository;

    @GetMapping
    public List<MovieDto> getAllMovies() {
        return this.repository.findAllProjectedBy();
    }

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@RequestBody Movie movie) {
        if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        Movie createdMovie = this.repository.save(movie);
        createdMovie.setScreenings(new ArrayList<>());
        return new ResponseEntity<>(this.translateToDto(createdMovie), HttpStatus.CREATED);
    }

    public MovieDto translateToDto(Movie movie) {
        return new MovieDto(movie.getId(), movie.getTitle(), movie.getRating(), movie.getDescription(), movie.getRuntimeMins(), movie.getCreatedAt(), movie.getUpdatedAt());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MovieDto> deleteMovie(@PathVariable int id) {
        Movie movie = this.repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        try {
            this.repository.delete(movie);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Movie still references a screening");
        }
        movie.setScreenings(new ArrayList<>());
        return ResponseEntity.ok(this.translateToDto(movie));
    }
}
