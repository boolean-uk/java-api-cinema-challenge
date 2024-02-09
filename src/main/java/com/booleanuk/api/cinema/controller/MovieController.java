package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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

    @GetMapping("{id}/screenings")
    public List<Screening> getAllScreenings(@PathVariable(name="id") Integer id) {
        Movie movie = this.movieRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cant find movie"));

        return movie.getScreenings();
    }

    @GetMapping("{id}")
    public ResponseEntity<Movie> getAMovie(@PathVariable(name="id") Integer id) {
        Movie movie = this.movieRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cant find movie"));

        return ResponseEntity.ok(movie);
    }



    //Post a movie
    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie) {
        //Dates
        LocalDateTime currentDateTime = LocalDateTime.now();
        movie.setCreatedAt(currentDateTime);
        movie.setUpdatedAt(null);

        Movie createdMovie = this.movieRepository.save(movie);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    //Post a screening
    @PostMapping("{id}/screenings")
    public ResponseEntity<Screening> createScreening(@PathVariable(name="id") Integer id,@RequestBody Screening screening) {
        Movie movie = this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the movie"));
        screening.setMovie(movie);
        //Dates
        LocalDateTime currentDateTime = LocalDateTime.now();
        screening.setCreatedAt(currentDateTime);
        screening.setUpdatedAt(null);

        return new ResponseEntity<>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateAMovie(@PathVariable int id,@RequestBody Movie movie){
        Movie movieToUpdate = this.movieRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the movie...."));

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        //Set update to right now
        LocalDateTime currentDateTime = LocalDateTime.now();
        movieToUpdate.setUpdatedAt(currentDateTime);
        return new ResponseEntity<>(this.movieRepository.save(movieToUpdate),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteAnMovie(@PathVariable int id){
        Movie movieToDelete = this.movieRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the movie!!!"));
        this.movieRepository.delete(movieToDelete);
        return ResponseEntity.ok(movieToDelete);
    }
}