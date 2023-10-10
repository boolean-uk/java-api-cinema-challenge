package com.booleanuk.api.cinema.movie;

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
    private MovieRepository movieRepository;

    @GetMapping
    public List<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields are invalid");
        }



        Date today = new Date();
        movie.setCreatedAt(today);
        movie.setUpdatedAt(today);

        return new ResponseEntity<>(this.movieRepository.save(movie), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields are invalid");
        } else {
            Movie movieToUpdate = this.movieRepository.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found to update"));

            movieToUpdate.setTitle(movie.getTitle());
            movieToUpdate.setRating(movie.getRating());
            movieToUpdate.setDescription(movie.getDescription());
            movieToUpdate.setRuntimeMins(movie.getRuntimeMins());

            Date today = new Date();
            movieToUpdate.setUpdatedAt(today);

            return new ResponseEntity<>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found to delete"));
        this.movieRepository.delete(movieToDelete);

        return ResponseEntity.ok(movieToDelete);
    }
}
