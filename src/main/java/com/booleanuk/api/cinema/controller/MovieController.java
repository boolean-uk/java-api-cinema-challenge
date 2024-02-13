package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.dto.MovieDto;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.response.ApiException;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    MovieRepository repository;

    @GetMapping
    public ResponseEntity<Response<List<MovieDto>>> getAllMovies() {
        return ResponseEntity.ok(new Response<>(this.repository.findAllProjectedBy()));
    }

    @PostMapping
    public ResponseEntity<Response<MovieDto>> createMovie(@RequestBody Movie movie) {
        if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() <= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "bad request");
        }
        Movie createdMovie;
        if (movie.getScreenings() == null || movie.getScreenings().isEmpty()) {
            createdMovie = this.repository.save(movie);
            createdMovie.setScreenings(new ArrayList<>());
        } else {
            for (Screening screening : movie.getScreenings()) {
                screening.setMovie(movie);
            }
            createdMovie = this.repository.save(movie);
        }
        return new ResponseEntity<>(new Response<>(this.translateToDto(createdMovie)), HttpStatus.CREATED);
    }

    public MovieDto translateToDto(Movie movie) {
        return new MovieDto(movie.getId(), movie.getTitle(), movie.getRating(), movie.getDescription(), movie.getRuntimeMins(), movie.getCreatedAt(), movie.getUpdatedAt());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<MovieDto>> deleteMovie(@PathVariable int id) {
        Movie movie = this.repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "not found"));
        try {
            this.repository.delete(movie);
        } catch (Exception e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Movie still references a screening");
        }
        movie.setScreenings(new ArrayList<>());
        return ResponseEntity.ok(new Response<>(this.translateToDto(movie)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<MovieDto>> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() < 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "bad request");
        }
        Movie updateMovie = this.repository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "not found"));
        updateMovie.setTitle(movie.getTitle());
        updateMovie.setRating(movie.getRating());
        updateMovie.setDescription(movie.getDescription());
        updateMovie.setRuntimeMins(movie.getRuntimeMins());

        return new ResponseEntity<>(new Response<>(this.translateToDto(this.repository.save(updateMovie))), HttpStatus.CREATED);
    }
}
