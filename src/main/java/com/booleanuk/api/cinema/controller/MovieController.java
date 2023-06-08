package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<Movie>> findAll() {
        return ResponseEntity.ok(this.movieRepository.findAll());
    }
    //TODO: remove this and its tests. Not specified in specs. OR Better, add to specs. Ask Dave
    @GetMapping("/{id}")
    public ResponseEntity<Movie> findMovieById(@PathVariable long id) {
        Optional<Movie> movieOptional = this.movieRepository.findById(id);
        return movieOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie savedMovie = this.movieRepository.save(movie);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedMovie.getId()).toUri();
        return ResponseEntity.created(location).body(savedMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable long id, @RequestBody Movie movie) {
        Optional<Movie> movieOptional = this.movieRepository.findById(id);
        if (movieOptional.isEmpty()) return ResponseEntity.notFound().build();

        Movie movieToUpdate = movieOptional.get();
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());

        return ResponseEntity.status(HttpStatus.CREATED).body(this.movieRepository.save(movieToUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable long id) {
        if(this.movieRepository.existsById(id)) {
            Optional<Movie> movie = this.movieRepository.findById(id);
            this.movieRepository.deleteById(id);
            return ResponseEntity.ok(movie.get());
        }
        return ResponseEntity.notFound().build();
    }
}
