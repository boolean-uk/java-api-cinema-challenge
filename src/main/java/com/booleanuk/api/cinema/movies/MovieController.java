package com.booleanuk.api.cinema.movies;

import com.booleanuk.api.cinema.responses.*;
import com.booleanuk.api.cinema.screenings.Screening;
import com.booleanuk.api.cinema.screenings.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    private ErrorResponse errorResponse = new ErrorResponse();
    private MovieResponse movieResponse = new MovieResponse();
    private MovieListResponse movieListResponse = new MovieListResponse();
    private ScreeningResponse screeningResponse = new ScreeningResponse();

    @GetMapping
    public ResponseEntity<Response<?>> getAllMovies() {
        this.movieListResponse.set(this.movieRepository.findAll());
        return ResponseEntity.ok(movieListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createMovie(@RequestBody Movie movie) {
        movie.setCreatedAt(LocalDateTime.now());
        movie.setUpdatedAt(LocalDateTime.now());
        if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() <= 0) {
            this.errorResponse.set("Could not create a new movie, please check all fields are correct");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        this.movieResponse.set(this.movieRepository.save(movie));
        if (!(movie.getScreenings() == null)) {
            for (Screening screening : movie.getScreenings()) {
                screening = new Screening(movie, screening.getScreenNumber(), screening.getStartsAt(), screening.getCapacity());
                this.screeningResponse.set(this.screeningRepository.save(screening));
            }
        }
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getMovieById(@PathVariable int id) {
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie == null) {
            this.errorResponse.set("No movie with that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.movieResponse.set(movie);
        return ResponseEntity.ok(movieResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.movieRepository.findById(id).orElse(null);
        if (movieToUpdate == null) {
            this.errorResponse.set("No movie with that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() <= 0) {
            this.errorResponse.set("Could not update the specified movie, please check all fields are correct");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        movieToUpdate.setUpdatedAt(LocalDateTime.now());
        this.movieResponse.set(this.movieRepository.save(movieToUpdate));
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteMovieById(@PathVariable int id) {
        Movie movieToDelete = this.movieRepository.findById(id).orElse(null);
        if (movieToDelete == null) {
            this.errorResponse.set("No movie with that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.movieRepository.delete(movieToDelete);
        this.movieResponse.set(movieToDelete);
        return ResponseEntity.ok(movieResponse);
    }



}