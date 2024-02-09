package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
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
    private MovieRepository movieRepository;

    @GetMapping
    public List<Movie> getAll() {
        return this.movieRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        if(containsNull(movie)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create movie, please check all required fields are correct.");
        }
        return new ResponseEntity<>(movieRepository.save(movie), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable int id) {
        Movie movie = findMovie(id);
        return ResponseEntity.ok(movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = findMovie(id);
        if(!movieToDelete.getScreenings().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete the movie because it has screenings attached to it.");
        }
        movieRepository.delete(movieToDelete);
        return ResponseEntity.ok(movieToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = findMovie(id);
        if(containsNull(movie)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the movie, please check all required fields are correct.");
        }
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());

        return new ResponseEntity<>(movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

    private Movie findMovie(int id) {
        return movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies with that id were found"));
    }

    private boolean containsNull(Movie movie) {
        return movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() == null;
    }
}


