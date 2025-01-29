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


@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;


    @GetMapping
    public ResponseEntity<?> getAllScreening(@PathVariable int id){
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if(movie == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        ScreeningListResponse screeningListResponse = new ScreeningListResponse();
        screeningListResponse.set(this.screeningRepository.getScreeningByMovie(movie));

        return ResponseEntity.ok(screeningListResponse);
    }



    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Response<?>> create(@PathVariable int id, @RequestBody Screening request) {
        ScreeningResponse screeningResponse = new ScreeningResponse();

        try {
            Movie movie = this.movieRepository.findById(id).orElse(null);
            if(movie == null){
                ErrorResponse error = new ErrorResponse();
                error.set("not found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            request.setMovie(movie);
            screeningResponse.set(this.screeningRepository.save(request));

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }
}
