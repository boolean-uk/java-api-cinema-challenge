package com.booleanuk.api.cinema.movie;

import com.booleanuk.api.cinema.customer.Customer;
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
    LocalDateTime currentTime = LocalDateTime.now();

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<Movie> getAll() {
        return this.movieRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable int id) {
        Movie movie = this.movieRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        return ResponseEntity.ok(movie);
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        movie.setCreatedAt(currentTime);
        movie.setUpdatedAt(currentTime);
        return new ResponseEntity<>(this.movieRepository.save(movie), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        Movie deleted = this.movieRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        this.movieRepository.delete(deleted);
        return ResponseEntity.ok(deleted);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie (@PathVariable int id, @RequestBody Movie movie) {

        Movie movieToUpdate = this.movieRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRunTimeMins(movie.getRunTimeMins());
        movieToUpdate.setUpdatedAt(currentTime);
        return new ResponseEntity<>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

}
