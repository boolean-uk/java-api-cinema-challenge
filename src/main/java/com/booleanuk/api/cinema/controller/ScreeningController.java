package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@RestController
@RequestMapping("/movies/{movieId}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;

    @PostMapping
    public ResponseEntity<Response<?>> create(@PathVariable int movieId, @RequestBody Screening screening) {
        Movie movie = this.movieRepository.findById(movieId).orElse(null);
        if(movie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("movie not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        screening.setMovie(movie);
        screening.setTickets(new ArrayList<>());
        screening.setStartsAt(screening.getStartsAt());
        screening.setCreatedAt(String.valueOf(LocalDateTime.now()));
        screening.setUpdatedAt(screening.getCreatedAt());
        Response<Screening> screeningResponse = new Response<>();
        screeningResponse.set(this.screeningRepository.save(screening));
        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<?>> getAll(@PathVariable int movieId) {
        Movie movie = this.movieRepository.findById(movieId).orElse(null);
        if(movie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("movie not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Response<List<Screening>> screeningResponse = new Response<>();
        screeningResponse.set(movie.getScreenings());
        return ResponseEntity.ok(screeningResponse);
    }

}
