package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.ScreeningListResponse;
import com.booleanuk.api.cinema.response.ScreeningResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TicketRepository ticketRepository;

    private ScreeningResponse screeningResponse = new ScreeningResponse();
    private ScreeningListResponse screeningListResponse = new ScreeningListResponse();

    @GetMapping
    public ResponseEntity<Response<?>> getAllScreening(@PathVariable int id) {
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that id found");

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        List<Screening> screeningsList = this.screeningRepository.findByMovie(movie);
        this.screeningListResponse.set(screeningsList);

        return ResponseEntity.ok(screeningListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createScreening(@PathVariable int id, @RequestBody Screening screening){
        if (screening.getScreenNumber() <= 0 || screening.getCapacity() <= 0 || screening.getStartsAt() == null){
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a screening for the specified movie, please check all fields are correct");

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that id found");

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        screening.setMovie(movie);
        screening.setCreatedAt(LocalDateTime.now());
        screening.setUpdatedAt(LocalDateTime.now());
        movie.addScreening(screening);

        this.screeningRepository.save(screening);
        this.screeningResponse.set(screening);

        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }

}
