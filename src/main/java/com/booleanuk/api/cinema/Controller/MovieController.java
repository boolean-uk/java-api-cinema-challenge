package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.Model.Movie;
import com.booleanuk.api.cinema.Model.Screening;
import com.booleanuk.api.cinema.Repository.MovieRepository;
import com.booleanuk.api.cinema.Repository.ScreeningRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.transaction.Transactional;
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

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public List<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        return new ResponseEntity<Movie>(this.movieRepository.save(movie), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable int id) {
        Movie movie = null;
        movie = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies matching that id were found"));
        return ResponseEntity.ok(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies matching that id were found"));

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        return new ResponseEntity<Movie>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies matching that id were found"));

        //Saving the screenings in a list
        List<Screening> screenings = movieToDelete.getScreenings();

        //Delete the associated screenings
        for (Screening screening : screenings) {
            this.screeningRepository.delete(screening);
        }

        //Delete movie entity
        this.movieRepository.delete(movieToDelete);

        return ResponseEntity.ok(movieToDelete);
    }
}
