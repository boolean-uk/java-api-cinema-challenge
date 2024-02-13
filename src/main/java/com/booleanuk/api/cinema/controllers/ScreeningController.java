package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;

    private LocalDateTime today;

    @GetMapping
    public ResponseEntity<Response<?>> findAll(@PathVariable int id){
        Movie tempMovie = findTheMovie(id);
        if (tempMovie == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        ScreeningListResponse screeningListResponse = new ScreeningListResponse();
        screeningListResponse.set(tempMovie.getScreenings());
        return ResponseEntity.ok(screeningListResponse);
    }


    @PostMapping
    public ResponseEntity<Response<?>> addScreening(@PathVariable int id, @RequestBody Screening screening){
        today = LocalDateTime.now();

        Movie tempMovie = findTheMovie(id);

        if (tempMovie == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        if (screening.getScreenNumber() < 1 ||
                screening.getCapacity() < 1 ||
                screening.getStartsAt() == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        screening.setMovie(tempMovie);
        screening.setCreatedAt(String.valueOf(today));

        ScreeningResponse screeningResponse = new ScreeningResponse();
        screeningResponse.set(this.screeningRepository.save(screening));

       return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }



    private Movie findTheMovie(int id){
        return this.movieRepository.findById(id)
                .orElse(null);
    }

}
