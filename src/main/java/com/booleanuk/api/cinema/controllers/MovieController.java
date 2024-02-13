package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.MovieListResponse;
import com.booleanuk.api.cinema.responses.MovieResponse;
import com.booleanuk.api.cinema.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    private LocalDateTime today;

    @GetMapping
    public ResponseEntity<MovieListResponse> findAll(){
        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.set(this.movieRepository.findAll());
        return ResponseEntity.ok(movieListResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getMovieById(@PathVariable int id){
        Movie findMovie = findOne(id);

        if (findMovie == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(findMovie);

        return ResponseEntity.ok(movieResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> addMovie(@RequestBody Movie movie){
        today = LocalDateTime.now();

        if (movie.getTitle() == null ||
        movie.getRating() == null ||
        movie.getDescription() == null ||
        movie.getRuntimeMins() < 1){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        movie.setCreatedAt(String.valueOf(today));
        movie.setUpdatedAt(String.valueOf(today));

        if (movie.getScreenings() != null){
            for (Screening screening : movie.getScreenings()){
                screening.setCreatedAt(String.valueOf(today));
                screening.setMovie(movie);
            }
        }
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(this.movieRepository.save(movie));

        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateMovie(@PathVariable int id, @RequestBody Movie movie){
        today = LocalDateTime.now();

        Movie updateMovie = findOne(id);

        if (updateMovie == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }

        if (movie.getRuntimeMins() < 1){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }


        Optional.ofNullable(movie.getTitle())
                        .ifPresent(updateMovie::setTitle);

        Optional.ofNullable(movie.getRating())
                        .ifPresent(updateMovie::setRating);

        Optional.ofNullable(movie.getDescription())
                        .ifPresent(updateMovie::setDescription);

        Optional.ofNullable(movie.getRuntimeMins())
                        .ifPresent(updateMovie::setRuntimeMins);

        updateMovie.setUpdatedAt(String.valueOf(today));
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(this.movieRepository.save(updateMovie));
        return new ResponseEntity<>(movieResponse,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteMovie(@PathVariable int id){
        Movie deleteMovie = findOne(id);

        if (deleteMovie == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }

        this.movieRepository.delete(deleteMovie);
        deleteMovie.setScreenings(new ArrayList<>());

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(deleteMovie);

        return ResponseEntity.ok(movieResponse);
    }


    private Movie findOne(int id){
        return this.movieRepository.findById(id)
                .orElse(null);
    }
}
