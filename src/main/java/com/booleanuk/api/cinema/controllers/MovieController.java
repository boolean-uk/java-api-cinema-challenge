package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.MovieListResponse;
import com.booleanuk.api.cinema.response.MovieResponse;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    private MovieResponse movieResponse = new MovieResponse();
    private MovieListResponse movieListResponse = new MovieListResponse();

    @GetMapping
    public ResponseEntity<Response<?>> getAllMovies(){
        List<Movie> movies = this.movieRepository.findAll();
        this.movieListResponse.set(movies);

        return ResponseEntity.ok(movieListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createMovie(@RequestBody Movie movie){
        if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() <= 0){
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a new movie, please check all fields are correct");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        movie.setCreatedTime(LocalDateTime.now());
        movie.setUpdatedTime(LocalDateTime.now());

        this.movieRepository.save(movie);
        this.movieResponse.set(movie);

        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateMovie(@PathVariable int id, @RequestBody Movie movie){
        if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() <= 0){
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a new movie, please check all fields are correct");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Movie movieToUpdate = this.movieRepository.findById(id).orElse(null);
        if (movieToUpdate == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that id found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());

        this.movieRepository.save(movieToUpdate);
        this.movieResponse.set(movieToUpdate);

        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteMovie(@PathVariable int id){
        Movie movieToDelete = this.movieRepository.findById(id).orElse(null);
        if (movieToDelete == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that id found");

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        this.movieRepository.delete(movieToDelete);
        this.movieResponse.set(movieToDelete);

        return ResponseEntity.ok(movieResponse);
    }
}
