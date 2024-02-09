package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
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
    private MovieRepository repository;

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie) {
        Movie createdMovie = this.repository.save(movie);
        if(createdMovie.getScreenings() <= 0) {

        }
        createdMovie.setScreenings(new ArrayList<Screening>());
        return new ResponseEntity<Movie>(createdMovie, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Movie> getAll() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getById(@PathVariable int id) {
        Movie movie = null;
        movie = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntime_mins(movie.getRuntime_mins());

        return new ResponseEntity<Movie>(this.repository.save(movieToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> delete(@PathVariable int id) {
        Movie movieToDelete = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.repository.delete(movieToDelete);
        movieToDelete.setScreenings(new ArrayList<Screening>());
        return ResponseEntity.ok(movieToDelete);
    }

}
