package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.responses.ScreeningListResponse;
import com.booleanuk.api.cinema.responses.ScreeningResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("movies/{id}/screenings") // id = id of the movie
public class ScreeningController {

    @Autowired
    private ScreeningRepository screenings;

    @Autowired
    private MovieRepository movies;

    @PostMapping
    public ResponseEntity<Response<?>> create(@PathVariable(name = "id") int id, @RequestBody Screening toAdd) {
        Movie movie = this.movies.findById(id)
                .orElse(
                        null
                );

        // 404 not found
        if (movie == null) {
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        // 400 bad request
        if (toAdd.getCapacity() < 0 || toAdd.getScreenNumber() < 0) {
            ErrorResponse error = new ErrorResponse("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        toAdd.setMovie(movie);
        toAdd.setCreatedAt(LocalDateTime.now());
        toAdd.setUpdatedAt(LocalDateTime.now());
        movie.addScreening(toAdd);

        ScreeningResponse screeningResponse = new ScreeningResponse();
        screeningResponse.set(this.screenings.save(toAdd));

        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<?>> getOne(@PathVariable(name = "id") int id) {
        Movie movie = this.movies.findById(id)
                .orElse(
                        null
                );

        // 404 not found
        if (movie == null) {
            ErrorResponse error = new ErrorResponse("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        ScreeningListResponse screeningListResponse = new ScreeningListResponse();
        screeningListResponse.set(movie.getScreenings());

        return new ResponseEntity<>(screeningListResponse, HttpStatus.OK);
    }

}
