package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
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
    public List<Movie> getAllMovie() {
        return this.movieRepository.findAll();
    }

    private Movie getAMovie(int id) {
        return this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id were found"));
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
        return new ResponseEntity<>(this.movieRepository.save(movie), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable int id){
        Movie movie = this.getAMovie(id);
        return ResponseEntity.ok(this.getAMovie(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie){
        Movie movieToUpdate = this.getAMovie(id);
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        //movieToUpdate.setCreatedAt(movie.getCreatedAt());
        movieToUpdate.setUpdatedAt(movie.getUpdatedAt());

        return new ResponseEntity<>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Movie> deleteMovie (@PathVariable int id){
        Movie movieToDelete = this.getAMovie(id);

        //get the screening associated with the movie, first delete the screening then delete the movie
        List<Screening> screenings = movieToDelete.getScreenings();
        for (Screening screening : screenings){
            this.screeningRepository.delete(screening);
        }
        this.movieRepository.delete(movieToDelete);
        return  ResponseEntity.ok(movieToDelete);
    }


}
