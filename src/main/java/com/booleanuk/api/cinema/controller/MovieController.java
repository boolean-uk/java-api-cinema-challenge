package com.booleanuk.api.cinema.controller;


import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    @GetMapping
    public List<Movie> getAll(){
        return movieRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> get(@PathVariable int id){
        Movie movie = movieRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id were found"));
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Movie> create(@RequestBody Movie movie){
        if(movie.getTitle() == null || movie.getDescription() == null || movie.getRating() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create movie, please check all required fields are correct");
        }
        return new ResponseEntity<>(movieRepository.save(movie), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Movie> update(@RequestBody Movie movie, @PathVariable int id){
        Movie existingMovie = movieRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id were found"));
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setDescription(movie.getDescription());
        existingMovie.setRating(movie.getRating());
        existingMovie.setRuntimeMins(movie.getRuntimeMins());
        existingMovie.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));

        if(existingMovie.getTitle() == null || existingMovie.getRating() == null || existingMovie.getRuntimeMins() == 0 || existingMovie.getDescription() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update movie, please check all required fields are correct");
        }
        return new ResponseEntity<>(movieRepository.save(existingMovie), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> delete(@PathVariable int id){
        Movie movie = movieRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id were found"));
        movieRepository.delete(movie);
        return ResponseEntity.ok(movie);
    }
}
