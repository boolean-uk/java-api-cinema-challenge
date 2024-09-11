package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    MovieRepository repository;

    @Autowired
    ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<List<Movie>> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Movie> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(getMovie(id));
    }

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie) {
        movie.setCreatedAt(new Date());
        return new ResponseEntity<>(this.repository.save(movie), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Movie> update(@PathVariable Integer id, @RequestBody Movie updatedMovie) {
        Movie movie = getMovie(id);
        movie.setTitle(updatedMovie.getTitle());
        movie.setRating(updatedMovie.getRating());
        movie.setDescription(updatedMovie.getDescription());
        movie.setRunTimesMins(updatedMovie.getRunTimesMins());
        movie.setUpdatedAt(new Date());

        //Get original created at date for movie
        movie.setCreatedAt(movie.getCreatedAt());

        return new ResponseEntity<>(this.repository.save(movie), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Movie> delete(@PathVariable Integer id) {
        Movie movie = getMovie(id);
        this.repository.delete(movie);
        return ResponseEntity.ok(movie);
    }


    @GetMapping("{id}/screenings")
    public ResponseEntity<List<Screening>> getScreening(@PathVariable Integer id) {
        Movie movie = getMovie(id);
        return ResponseEntity.ok(movie.getScreenings());
    }

    @PostMapping("{id}/screenings")
    public ResponseEntity<Screening> createScreening(@PathVariable Integer id, @RequestBody Screening screening) {
        Movie movie = getMovie(id);

        screening.setCreatedAt(new Date());
        screening.setMovie(movie);
        movie.addScreening(screening);

        return new ResponseEntity<>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }

    private Movie getMovie(Integer id) {
        return this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find movie with given ID"));
    }
}
