package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.Model.Movie;
import com.booleanuk.api.cinema.Repository.MovieRepository;
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
    private MovieRepository repository;

    @GetMapping
    public List<Movie> getAll() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getById(@PathVariable int id) {
        Movie movie = this.repository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(movie);
    }

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie request) {
        //validate(request);
        Movie movie = new Movie(request.getTitle(), request.getRating(), request.getDescription(), request.getRuntimeMins());
        return new ResponseEntity<Movie>(this.repository.save(movie), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable int id, @RequestBody Movie request) {
        //validate(request);
        Movie movie = this.repository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        movie.setTitle(request.getTitle());
        movie.setRating(request.getRating());
        movie.setDescription(request.getDescription());
        movie.setRuntimeMins(request.getRuntimeMins());
        movie.updateUpdatedAt();
        return new ResponseEntity<Movie>(this.repository.save(movie), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> delete(@PathVariable int id) {
        Movie movie = this.repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        this.repository.delete(movie);
        return ResponseEntity.ok(movie);
    }

    public void validate(Movie Movie) {
        if (Movie.getTitle() == null || Movie.getRating() == null || Movie.getDescription() == null || Movie.getRuntimeMins() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create/update Movie, please check all required fields are correct.");
        }
    }

}
