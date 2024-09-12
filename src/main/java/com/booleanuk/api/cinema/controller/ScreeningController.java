package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("movie/{id}/screenings")
public class ScreeningController {

    @Autowired
    ScreeningRepository repository;

    @Autowired
    MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAll() {
        List<Screening> screenings = ResponseEntity.ok(repository.findAll()).getBody();
        ScreeningListResponse screeningListResponse = new ScreeningListResponse();
        screeningListResponse.set(screenings);
        return ResponseEntity.ok(screeningListResponse);
    }

    @GetMapping("{screeningId}")
    public ResponseEntity<Response<?>> getById(@PathVariable(name = "id") Integer id, @PathVariable(name = "screeningId") Integer screeningId) {
        Screening screening = this.repository.findById(screeningId).orElse(null);
        if(screening == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        ScreeningResponse screeningResponse = new ScreeningResponse();
        screeningResponse.set(screening);
        return ResponseEntity.ok(screeningResponse);
    }

    @PostMapping()
    public ResponseEntity<?> createScreening(@PathVariable(name = "id") Integer id, @RequestBody Screening screening) {
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if(movie==null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        screening.setCreatedAt(new Date());
        screening.setMovie(movie);
        movie.addScreening(screening);

        ScreeningResponse screeningResponse = new ScreeningResponse();
        screeningResponse.set(screening);
        this.repository.save(screening);

        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }
}
