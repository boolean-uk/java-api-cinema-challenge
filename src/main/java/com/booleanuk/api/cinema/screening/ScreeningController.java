package com.booleanuk.api.cinema.screening;

import com.booleanuk.api.cinema.movie.Movie;
import com.booleanuk.api.cinema.movie.MovieRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.ScreeningResponse;
import com.booleanuk.api.cinema.response.ScreeningResponseList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

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

    @DeleteMapping("screenings/{id}")
    public ResponseEntity<Response<?>> deleteScreening(@PathVariable int id) {
        Screening deletedScreening = this.screeningRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No screening with that id"));
        ScreeningResponse response = new ScreeningResponse();

        if (deletedScreening == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.screeningRepository.delete(deletedScreening);
        response.set(deletedScreening);
        return ResponseEntity.ok(response);
    }
}
