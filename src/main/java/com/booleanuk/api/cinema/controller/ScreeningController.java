package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.payload.response.ErrorResponse;
import com.booleanuk.api.cinema.payload.response.Response;
import com.booleanuk.api.cinema.payload.response.ScreeningListResponse;
import com.booleanuk.api.cinema.payload.response.ScreeningResponse;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/movies/{id}/screenings")
public class ScreeningController {


    @Autowired
    private ScreeningRepository repository;

    @Autowired
    private MovieRepository movieRepository;


    @GetMapping
    public ResponseEntity<Response<?>> getAll(@PathVariable int id) {
        var movie = movieRepository.findById(id).orElse(null);
        if (movie == null)
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);

        ScreeningListResponse response = new ScreeningListResponse(this.repository.findAll().stream().filter(
                (a) -> a.getId() == id
        ).toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @JsonView(Views.BasicInfo.class)
    public ResponseEntity<Response<?>> PostUser(@PathVariable int id,  @RequestBody Screening screening) {
        ScreeningResponse response = new ScreeningResponse();
        var movie = this.movieRepository.findById(id).orElse(null);
        if (movie == null)
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);

        screening.setMovie(movie);
        try {
            response.set(this.repository.save(screening));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
