package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    private LocalDateTime today;
    private DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm");

    @GetMapping
    public List<Movie> findAll(){
        return this.movieRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable int id){
        Movie findMovie = findOne(id);
        return ResponseEntity.ok(findMovie);
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
        today = LocalDateTime.now();

        movie.setCreatedAt(today.format(pattern));
        movie.setUpdatedAt(today.format(pattern));
        return new ResponseEntity<Movie>(this.movieRepository.save(movie),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie){
        today = LocalDateTime.now();

        Movie updateMovie = findOne(id);
        updateMovie.setTitle(movie.getTitle());
        updateMovie.setRating(movie.getRating());
        updateMovie.setDescription(movie.getDescription());
        updateMovie.setRuntimeMins(movie.getRuntimeMins());
        updateMovie.setUpdatedAt(today.format(pattern));
        return new ResponseEntity<Movie>(this.movieRepository.save(updateMovie),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id){
        Movie deleteMovie = findOne(id);
        this.movieRepository.delete(deleteMovie);
        deleteMovie.setScreenings(new ArrayList<>());
        return ResponseEntity.ok(deleteMovie);
    }


    private Movie findOne(int id){
        return this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
