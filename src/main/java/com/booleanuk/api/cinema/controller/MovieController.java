package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
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
    public ResponseEntity<List<Movie>> getAll() {
        return new ResponseEntity<>(this.movies.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Movie> getOne(@PathVariable int id) {
        Movie toReturn = this.movies.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
        return new ResponseEntity<>(toReturn, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie toAdd) {
        toAdd.setCreatedAt(LocalDateTime.now());
        toAdd.setUpdatedAt(LocalDateTime.now());
        return new ResponseEntity<>(this.movies.save(toAdd), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Movie> delete(@PathVariable int id) {
        Movie toDelete = this.movies.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
        this.movies.delete(toDelete);
        return new ResponseEntity<>(toDelete, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Movie> update(@PathVariable(name = "id") int id, @RequestBody Movie newData) {
        Movie toUpdate = this.movies.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );

        toUpdate.setTitle(newData.getTitle());
        toUpdate.setDescription(newData.getDescription());
        toUpdate.setRating(newData.getRating());
        toUpdate.setRuntimeMins(newData.getRuntimeMins());
        toUpdate.setUpdatedAt(LocalDateTime.now());

        return new ResponseEntity<>(this.movies.save(toUpdate), HttpStatus.CREATED);
    }

}
