package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public List<Movie> getAllMovies(){
        return this.movieRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
        movie.setCreatedTime(LocalDateTime.now());
        movie.setUpdatedTime(LocalDateTime.now());

        return new ResponseEntity<Movie>(this.movieRepository.save(movie), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie){
        Movie movieToUpdate = this.movieRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());

        return new ResponseEntity<Movie>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id){
        Movie movieToDelete = this.movieRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        this.movieRepository.delete(movieToDelete);
        return ResponseEntity.ok(movieToDelete);
    }
}
