package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.ApiResponse;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public ApiResponse<List<Movie>> getAllMovies() {
        return new ApiResponse<>("success", this.movieRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable int id) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found!"));
        return ResponseEntity.ok(movie);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Movie>> createMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        return new ResponseEntity<>(new ApiResponse<>("success", savedMovie), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Movie>> updateMovie(@PathVariable int id, @RequestBody Movie movieDetails) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        return optionalMovie.map(movie -> {
            movie.setTitle(movieDetails.getTitle());
            movie.setDescription(movieDetails.getDescription());
            movie.setRating(movieDetails.getRating());
            movie.setRuntimeMins(movieDetails.getRuntimeMins());

            Movie updatedMovie = movieRepository.save(movie);
            return new ResponseEntity<>(new ApiResponse<>("success", updatedMovie), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Movie>> deleteMovie(@PathVariable int id) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }

        movieRepository.delete(movie);

        return ResponseEntity.ok(new ApiResponse<>("success", movie));
    }
}
