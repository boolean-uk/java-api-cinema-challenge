package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public List<Movie> getAllMovies(){
        return this.movieRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        if (movie.getScreenings() != null && (!movie.getScreenings().isEmpty())) {
            for(Screening screening : movie.getScreenings()) {
                screening.setMovie(movie);
            }
        } else {
            movie.setScreenings(new ArrayList<Screening>());
        }
        Movie createdMovie = this.movieRepository.save(movie);
        return new ResponseEntity<Movie>(createdMovie, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie){
        Movie movie1 = this.getAMovie(id);
        movie1.setTitle(movie.getTitle());
        movie1.setRating(movie.getRating());
        movie1.setDescription(movie.getDescription());
        movie1.setRuntimeMins(movie.getRuntimeMins());
        movie1.setUpdatedAt(ZonedDateTime.now());

        return new ResponseEntity<>(this.movieRepository.save(movie1), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id){
        Movie movie1 = this.getAMovie(id);
        this.movieRepository.delete(movie1);
        return ResponseEntity.ok(movie1);
    }
    private Movie getAMovie(int id){
        return this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Movie with that ID found"));
    }
}
