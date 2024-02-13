package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.ScreeningListResponse;
import com.booleanuk.api.cinema.response.ScreeningResponse;
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
    public ResponseEntity<Response<?>> getAllScreenings(@PathVariable int id){
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        List<Screening> screening = movie.getScreenings();
        ScreeningListResponse screeningListResponse = new ScreeningListResponse();
        screeningListResponse.set(screening);
        return ResponseEntity.ok(screeningListResponse);
    }
    @PostMapping("movies/{id}/screenings")
    public ResponseEntity<Response<?>> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Screening screening1 = new Screening();
        try {
            screening1.setScreenNumber(screening.getScreenNumber());
            screening1.setCapacity(screening.getCapacity());
            screening1.setStartsAt(screening.getStartsAt());
            screening1.setMovie(this.movieRepository.findById(id).orElse(null));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create screening for that movie");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        this.screeningRepository.save(screening1);
        ScreeningResponse screeningResponse = new ScreeningResponse();
        screeningResponse.set(screening1);
        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }
}
