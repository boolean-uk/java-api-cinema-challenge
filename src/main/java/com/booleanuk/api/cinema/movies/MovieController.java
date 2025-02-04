package com.booleanuk.api.cinema.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public Movie getOneMovie(@PathVariable int id) {
        return this.movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found.")
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        Movie newMovie = new Movie(movie.getTitle(), movie.getRating(), movie.getDescription(), movie.getRuntimeMins());
        return this.movieRepository.save(newMovie);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{id}")
    public Movie updateMovie(@RequestBody Movie movie, @PathVariable int id) {
        Movie movieToUpdate = this.movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found.")
        );
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        return this.movieRepository.save(movieToUpdate);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    public Movie deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found.")
        );
        this.movieRepository.delete(movieToDelete);
        return movieToDelete;
    }
}
