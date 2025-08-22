package com.booleanuk.api.cinema.screenings;

import com.booleanuk.api.cinema.movies.MovieRepository;
import com.booleanuk.api.cinema.responses.*;
import com.booleanuk.api.cinema.movies.Movie;
import com.booleanuk.api.cinema.tickets.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("movie/{id}/screenings")
public class ScreeningController {

    @Autowired
    private  ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public ResponseEntity<ScreeningListResponse>getById(@PathVariable("id") int id) {
        ScreeningListResponse screeningListResponse = new ScreeningListResponse();

        Movie movie = this.movieRepository.findById(id).orElse(null);
        screeningListResponse.set(screeningRepository.getScreeningByMovie(movie));
        return ResponseEntity.ok(screeningListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Screening screening, @PathVariable("id") int id) {
        Movie theMovie = this.movieRepository.findById(id).orElse(null);
        if (theMovie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Movie not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        screening.setMovie(theMovie);
        ScreeningResponse screeningResponse = new ScreeningResponse();
        try {
            screeningResponse.set(this.screeningRepository.save(screening));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }
}
