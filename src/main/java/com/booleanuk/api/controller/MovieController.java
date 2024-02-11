package com.booleanuk.api.controller;

import com.booleanuk.api.model.Movie;
import com.booleanuk.api.model.Screening;
import com.booleanuk.api.repository.MovieRepository;
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
    private MovieRepository movieRepository;

    @GetMapping
    public List<Movie> getAll(){
        return this.movieRepository.findAll();
    }
    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie employee){
        return new ResponseEntity<Movie>(this.movieRepository.save(employee), HttpStatus.CREATED) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getById(@PathVariable int id){
        Movie employee = this.movieRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        return ResponseEntity.ok(employee);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> delete(@PathVariable int id) {
        Movie toDelete = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No publisher with ID"));
        this.movieRepository.delete(toDelete);
        toDelete.setScreenings(new ArrayList<Screening>());
        return ResponseEntity.ok(toDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable int id, @RequestBody Movie employee){
        Movie update = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        update.setTitle(employee.getTitle());
        update.setRating(employee.getRating());
        update.setDescription(employee.getDescription());
        update.setRuntimeMins(employee.getRuntimeMins());
        update.setCreatedAt(employee.getCreatedAt());
        update.setUpdatedAt(employee.getUpdatedAt());
        update.setScreenings(new ArrayList<>());
        return new ResponseEntity<>(this.movieRepository.save(update), HttpStatus.CREATED);
    }
}
