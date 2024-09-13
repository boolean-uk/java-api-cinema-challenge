package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.DTO.BadRequestException;
import com.booleanuk.api.cinema.DTO.NotFoundException;
import com.booleanuk.api.cinema.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        return new ResponseEntity<>(this.movieRepository.save(movie), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        if(movie.getTitle() == null || movie.getDescription().length() < 2) {
            throw new BadRequestException("bad request");
        }
        Movie movieToUpdate = this.movieRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No movie with that ID found")
        );
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());

        movie.setId(movieToUpdate.getId());
        return new ResponseEntity<>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.movieRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No movie with that ID found")
        );
        this.movieRepository.delete(movieToDelete);
        return ResponseEntity.ok(movieToDelete);
    }

}
