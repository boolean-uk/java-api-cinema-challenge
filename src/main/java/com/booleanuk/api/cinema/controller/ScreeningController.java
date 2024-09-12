package com.booleanuk.api.cinema.controller;


import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.ScreeningListResponse;
import com.booleanuk.api.cinema.response.ScreeningResponse;
import com.booleanuk.api.cinema.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {

    @Autowired
    private ScreeningRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    public ScreeningController(ScreeningRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<Object> getAll(@PathVariable int id) {
        ScreeningListResponse screeningListResponse = new ScreeningListResponse();
        List<Screening> screenings = this.repository.findAllByMovie_id(id);
        if (screenings.isEmpty()) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        screeningListResponse.set(screenings);
        return ResponseEntity.ok(screeningListResponse);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Object> createScreening(@PathVariable int id, @RequestBody Screening screening) {

        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        screening.setMovie(movie);

        ScreeningResponse screeningResponse = new ScreeningResponse();
        screeningResponse.set(this.repository.save(screening));
        return ResponseEntity.ok(screeningResponse);
    }

}

