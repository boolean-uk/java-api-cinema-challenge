package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie) {
        movie.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        movie.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        List<Screening> tempScreenings = movie.getScreenings();
        movie.setScreenings(null);
        this.movieRepository.save(movie);
        for(Screening s : tempScreenings){
            s.setMovie(movie);
            s.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            s.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        }
        screeningRepository.saveAll(tempScreenings);
        movie.setScreenings(tempScreenings);
        return new ResponseEntity<>(movie, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Movie> getAll() {
        return this.movieRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Movie> getOne(@PathVariable int id) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id")
        );
        return ResponseEntity.ok(movie);
    }

    @PutMapping("{id}")
    public ResponseEntity<Movie> update(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id")
        );
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        movieToUpdate.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return new ResponseEntity<>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Movie> delete(@PathVariable int id) {
        Movie movieToDelete = this.movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id")
        );
        this.movieRepository.delete(movieToDelete);
        return ResponseEntity.ok(movieToDelete);
    }
}
