package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("movies/{id}/screenings")
    public ResponseEntity<Response<?>> getScreenings(@PathVariable int id) {
        ScreeningListResponse screeningListResponse = new ScreeningListResponse();

        List<Screening> screenings = this.screeningRepository.getScreeningByMovieId(id);

        if (screenings.isEmpty()) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        screeningListResponse.set(screenings);
        return ResponseEntity.ok(screeningListResponse);
    }

    @PostMapping("/movies/{id}/screenings")
    public ResponseEntity<Response<?>> createScreening(@RequestBody Screening screening, @PathVariable int id) {
        ScreeningResponse screeningResponse = new ScreeningResponse();
        Movie movie = this.movieRepository.findById(id).orElse(null);

        if (movie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        screening.setMovie(movie);

        if (screening.getScreenNumber() == 0 || screening.getCapacity() == 0 || screening.getStartsAt() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        try {
            screeningResponse.set(this.screeningRepository.save(screening));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }
}
