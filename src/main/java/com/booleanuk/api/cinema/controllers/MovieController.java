package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.AttributedCharacterIterator;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    private LocalDateTime today;
    private DateTimeFormatter pattern = DateTimeFormatter.ISO_LOCAL_DATE_TIME.localizedBy(Locale.UK);

    @GetMapping
    public List<Movie> findAll(){
        return this.movieRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable int id){
        Movie findMovie = findOne(id);
        return ResponseEntity.ok(findMovie);
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
        today = LocalDateTime.now();

        movie.setCreatedAt(today.format(pattern));
        movie.setUpdatedAt(today.format(pattern));

        if (movie.getScreenings() != null){
            for (Screening screening : movie.getScreenings()){
                screening.setCreatedAt(today.format(pattern));
                screening.setMovie(movie);
            }
        }

        return new ResponseEntity<Movie>(this.movieRepository.save(movie),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie){
        today = LocalDateTime.now();

        Movie updateMovie = findOne(id);

        Optional.ofNullable(movie.getTitle())
                        .ifPresent(title -> updateMovie.setTitle(title));

        Optional.ofNullable(movie.getRating())
                        .ifPresent(rating -> updateMovie.setRating(rating));

        Optional.ofNullable(movie.getDescription())
                        .ifPresent(description -> updateMovie.setDescription(description));

        Optional.ofNullable(movie.getRuntimeMins())
                        .ifPresent(runtimeMins -> updateMovie.setRuntimeMins(runtimeMins));

        updateMovie.setUpdatedAt(today.format(pattern));

        return new ResponseEntity<Movie>(this.movieRepository.save(updateMovie),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id){
        Movie deleteMovie = findOne(id);
        this.movieRepository.delete(deleteMovie);
        deleteMovie.setScreenings(new ArrayList<>());
        return ResponseEntity.ok(deleteMovie);
    }


    private Movie findOne(int id){
        return this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
