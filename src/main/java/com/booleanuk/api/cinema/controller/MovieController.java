
package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
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
    private MovieRepository movieRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getAll() {
        return this.movieRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie create(@RequestBody Movie body) {
        return this.movieRepository.save(body);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Movie update(@PathVariable int id, @RequestBody Movie body) {
        return this.movieRepository.findById(id)
                .map(movie -> {
                    movie.setTitle(body.getTitle());
                    movie.setRating(body.getRating());
                    movie.setDescription(body.getDescription());
                    movie.setRuntimeMin(body.getRuntimeMin());
                    return this.movieRepository.save(movie);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie found for id: " + id));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Movie> delete(@PathVariable int id) {
        return this.movieRepository.findById(id)
                .map(movie -> {
                    this.movieRepository.delete(movie);
                    return new ResponseEntity<>(movie, HttpStatus.OK);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie found for id: " + id));
    }
}
