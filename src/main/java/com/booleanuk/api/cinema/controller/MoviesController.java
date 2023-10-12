package com.booleanuk.api.cinema.controller;


import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    @Autowired
    private final MoviesRepository moviesRepository;

    public MoviesController(MoviesRepository repository) {
        this.moviesRepository = repository;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie customerCreated(@RequestBody Movie newMovie) throws SQLException {


        //////////////////////////////////////////////////////////////////////////
        //Set date for new movie
        OffsetDateTime now = OffsetDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String timestamp = now.format(formatter);
        newMovie.setCreatedAt(timestamp);
        newMovie.setUpdatedAt(timestamp);
        //////////////////////////////////////////////////////////////////////////
        newMovie.setRuntimeMins(newMovie.getRuntimeMins());
        return this.moviesRepository.save(newMovie);
    }

    @GetMapping
    public List<Movie> getAll() {
        return this.moviesRepository.findAll();
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable int id) {
        Movie movie = null;
        movie = this.moviesRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie was not found")
        );
        return movie;
    }


    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.moviesRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie was not found")
        );


        OffsetDateTime now = OffsetDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String timestamp = now.format(formatter);

        movie.setUpdatedAt(timestamp);

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());

        movieToUpdate.setUpdatedAt(timestamp);
        return new ResponseEntity<>(this.moviesRepository.save(movieToUpdate), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteCustomerById(@PathVariable int id) {
        Movie movieToDelete = this.moviesRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie was not found")
        );

        moviesRepository.delete(movieToDelete);
        //ResponseEntity.ok stuurt een status code 200 terug, met movieToDelete als value
        //In echte projecten 'return ResponseEntity.noContent().build();' gebruiken ipv .ok
        //Dit stuurt een code 204 terug , 204 = no content
        return ResponseEntity.ok(movieToDelete);
    }


}
