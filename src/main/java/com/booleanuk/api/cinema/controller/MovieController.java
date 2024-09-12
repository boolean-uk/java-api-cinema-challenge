package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    MovieRepository repository;

    @Autowired
    ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAll() {
        List<Movie> movies = ResponseEntity.ok(repository.findAll()).getBody();
        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.set(movies);
        return ResponseEntity.ok(movieListResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getById(@PathVariable Integer id) {
        Movie movie = this.repository.findById(id).orElse(null);
        if(movie == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movie);
        return ResponseEntity.ok(movieResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Movie movie) {
        if(movie.getTitle() == null ||movie.getDescription() == null || movie.getRating() == null || movie.getRunTimesMins() == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Bad Request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        movie.setCreatedAt(new Date());
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movie);
        this.repository.save(movie);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(@PathVariable Integer id, @RequestBody Movie updatedMovie) {
        if(updatedMovie.getTitle() == null || updatedMovie.getDescription() == null || updatedMovie.getRating() == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Movie movie = this.repository.findById(id).orElse(null);
        if(movie == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found!");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        movie.setTitle(updatedMovie.getTitle());
        movie.setRating(updatedMovie.getRating());
        movie.setDescription(updatedMovie.getDescription());
        movie.setRunTimesMins(updatedMovie.getRunTimesMins());
        movie.setUpdatedAt(new Date());

        //Get original created at date for movie
        movie.setCreatedAt(movie.getCreatedAt());

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movie);

        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Movie movie = this.repository.findById(id).orElse(null);
        if(movie==null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movie);
        this.repository.delete(movie);
        return ResponseEntity.ok(movieResponse);
    }

 /*
    @GetMapping("{id}/screenings")
    public ResponseEntity<?> getScreening(@PathVariable Integer id) {
        Movie movie = this.repository.findById(id).orElse(null);
        if(movie==null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        ScreeningListResponse screeningListResponse = new ScreeningListResponse();
        screeningListResponse.set(movie.getScreenings());

        return ResponseEntity.ok(movie.getScreenings());
    }

    @PostMapping("{id}/screenings")
    public ResponseEntity<?> createScreening(@PathVariable Integer id, @RequestBody Screening screening) {
        Movie movie = this.repository.findById(id).orElse(null);
        if(movie==null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        screening.setCreatedAt(new Date());
        screening.setUpdatedAt(new Date());
        screening.setMovie(movie);
        movie.addScreening(screening);

        return new ResponseEntity<>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }

  */
}
