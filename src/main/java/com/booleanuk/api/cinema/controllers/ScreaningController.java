package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.payload.response.ErrorResponse;
import com.booleanuk.api.cinema.payload.response.Response;
import com.booleanuk.api.cinema.payload.response.ScreeningListResponse;
import com.booleanuk.api.cinema.payload.response.ScreeningResponse;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreaningController {
    @Autowired
    ScreeningRepository repo;
    @Autowired
    MovieRepository movieRepo;

    @PostMapping
    public ResponseEntity<Response<?>> addOne(@PathVariable Integer id, @Valid @RequestBody Screening screening){
        Movie movie = this.movieRepo.findById(id).orElse(null);

        if(movie == null){
            ErrorResponse error = new ErrorResponse();
            error.set("Not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        screening.setMovie(movie);
        screening.setCreated(OffsetDateTime.now());
        screening.setUpdatedAt(OffsetDateTime.now());
        ScreeningResponse resp = new ScreeningResponse();
        resp.set(this.repo.save(screening));

        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<?>> getAll(@PathVariable Integer id){
        ScreeningListResponse resp = new ScreeningListResponse();
        Movie movie = this.movieRepo.findById(id).orElse(null);

        if(movie == null){
            ErrorResponse error = new ErrorResponse();
            error.set("Not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        resp.set(movie.getScreenings());
        return ResponseEntity.ok(resp);
    }
}
