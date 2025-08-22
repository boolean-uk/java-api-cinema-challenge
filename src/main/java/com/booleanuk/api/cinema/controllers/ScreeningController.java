package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.payload.response.ErrorResponse;
import com.booleanuk.api.cinema.payload.response.Response;
import com.booleanuk.api.cinema.payload.response.ScreeningListResponse;
import com.booleanuk.api.cinema.payload.response.ScreeningResponse;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class ScreeningController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @PostMapping("/movies/{id}/screenings")
    public ResponseEntity<Response<?>> createScreening(@PathVariable int id, @RequestBody MovieController.PostScreening request){
        ScreeningResponse screeningResponse = new ScreeningResponse();
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find movie with that id."));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX");
        Screening screening = new Screening(movie, request.screenNumber(), OffsetDateTime.parse(request.startsAt(), formatter), request.capacity());
        try {
            screeningResponse.set(this.screeningRepository.save(screening));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }

    @GetMapping("/movies/{id}/screenings")
    public ResponseEntity<ScreeningListResponse> getAllScreenings(@PathVariable int id) {
        ScreeningListResponse screeningListResponse = new ScreeningListResponse();
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find movie with that id."));
        screeningListResponse.set(this.screeningRepository.findByMovie(movie));
        return ResponseEntity.ok(screeningListResponse);
    }
}
