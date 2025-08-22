package com.booleanuk.api.cinema.controllers;

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

    @GetMapping
    public List<Movie> getAll(){
        return this.repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
        return new ResponseEntity<Movie>(this.repository.save(movie), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie){
        Movie movieToUpdate = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());

        return new ResponseEntity<>(this.repository.save(movieToUpdate), HttpStatus.CREATED);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Movie> DeleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        this.repository.delete(movieToDelete);
        return ResponseEntity.ok(movieToDelete);
    }
}
