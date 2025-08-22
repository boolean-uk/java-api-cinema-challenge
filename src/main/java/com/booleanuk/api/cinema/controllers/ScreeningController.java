package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.payloads.response.*;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;

@RestController
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("movies/{id}/screenings")
    public ResponseEntity<Response<?>> getAllScreenings(@PathVariable int id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));

        ScreeningListResponse screeningListResponse = new ScreeningListResponse();

        if (movie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        screeningListResponse.set(this.screeningRepository.findAll());
        return ResponseEntity.ok(screeningListResponse);
    }

    @PostMapping("movies/{id}/screenings")
    public ResponseEntity<Response<?>> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));

        ScreeningResponse screeningResponse = new ScreeningResponse();

        if (movie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        try {
            screening.setMovie(movie);
            screening.setCreatedAt(screening.getMovie().getCreatedAt());
            screening.setUpdatedAt(screening.getMovie().getUpdatedAt());
            screeningResponse.set(this.screeningRepository.save(screening));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }
}

    /*
      @PostMapping("movies/{id}/screenings")
    public ResponseEntity<Response<?>> createScreening(@RequestBody Screening screening, @PathVariable int id) {
        Movie movie = this.movieRepository.findById(id).orElse(null);
        ScreeningResponse response = new ScreeningResponse();

        if (movie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        try {
            screening.setMovie(movie);
            screening.setCreated_at(String.valueOf(LocalDateTime.now()));
            response.set(this.screeningRepository.save(screening));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("movies/{id}/screenings")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response<?>> getAllScreenings(@PathVariable int id) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No movie with that id"));
        ScreeningResponseList response = new ScreeningResponseList();

        if (movie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        response.set(movie.getScreenings());
        return ResponseEntity.ok(response);
    }
     */


//    @PostMapping
//    public ResponseEntity<Response<?>> createScreening(@RequestBody Screening screening) {
//        ScreeningResponse screeningResponse = new ScreeningResponse();
//        try {
//            screeningResponse.set(this.screeningRepository.save(screening));
//        } catch (Exception e) {
//            ErrorResponse error = new ErrorResponse();
//            error.set("Bad request");
//            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
//    }

    //public ResponseEntity<ScreeningListResponse> getAllScreenings() {
//        ScreeningListResponse screeningListResponse = new ScreeningListResponse();
//        screeningListResponse.set(this.screeningRepository.findAll());
//        return ResponseEntity.ok(screeningListResponse);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Response<?>> getScreeningById(@PathVariable int id) {
//        Screening screening = this.screeningRepository.findById(id).orElse(null);
//        if (screening == null) {
//            ErrorResponse error = new ErrorResponse();
//            error.set("not found");
//            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//        }
//        ScreeningResponse screeningResponse = new ScreeningResponse();
//        screeningResponse.set(screening);
//        return ResponseEntity.ok(screeningResponse);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Response<?>> updateScreening(@PathVariable int id, @RequestBody Screening screening) {
//        Screening screeningToUpdate = this.screeningRepository.findById(id).orElse(null);
//        if (screeningToUpdate == null) {
//            ErrorResponse error = new ErrorResponse();
//            error.set("not found");
//            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//        }
//
//
//        try {
//            movieToUpdate = this.movieRepository.save(movieToUpdate);
//        } catch (Exception e) {
//            ErrorResponse error = new ErrorResponse();
//            error.set("Bad request");
//            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//        }
//        MovieResponse movieResponse = new MovieResponse();
//        movieResponse.set(movieToUpdate);
//        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Response<?>> deleteMovie(@PathVariable int id) {
//        Movie movieToDelete = this.movieRepository.findById(id).orElse(null);
//        if (movieToDelete == null) {
//            ErrorResponse error = new ErrorResponse();
//            error.set("not found");
//            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//        }
//        this.movieRepository.delete(movieToDelete);
//        MovieResponse movieResponse = new MovieResponse();
//        movieResponse.set(movieToDelete);
//        return ResponseEntity.ok(movieResponse);
//    }
