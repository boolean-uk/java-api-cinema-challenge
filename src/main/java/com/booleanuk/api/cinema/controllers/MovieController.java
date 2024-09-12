package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository repository;

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie) {
        return new ResponseEntity<>(this.repository.save(movie), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<Movie> update(
            @PathVariable int id,
            @RequestBody Movie movie)
    {
        Movie originalMovie = getObjectById(id);
        movie.setId(id);
        movie.setCreatedAt(originalMovie.getCreatedAt());
        return new ResponseEntity<>(this.repository.save(movie), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Movie> delete(@PathVariable int id) {
        Movie movie = getObjectById(id);
        try {
            this.repository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not delete customer. Detailed information: "+e.getMessage()
            );
        }
        return ResponseEntity.ok(movie);
    }

    /**
     * Get object by id.
     * Can be used to check for valid id (throws exception if id doesn't exist).
     * @param id .
     * @return Customer
     */
    private Movie getObjectById(int id) {
        Movie movie = this.repository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No movie with id #"+id+" found."
                        )
                );
        return movie;
    }
}
