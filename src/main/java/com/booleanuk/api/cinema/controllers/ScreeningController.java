package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.CustomResponse;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/movies/{id}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository repo;

    @Autowired
    private MovieRepository movieRepo;

    @GetMapping
    public ResponseEntity<CustomResponse<List<Screening>>> getAll(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(
                new CustomResponse<>(
                        repo.findByMovieId(id)
                                .orElseThrow(() ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "No movies were found with that id"
                                        )
                                )
                ),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<CustomResponse<Screening>> createOne(@PathVariable(name = "id") int id, @RequestBody Screening screening) {
        screening.setMovie(
                movieRepo.findById(id)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "No movies were found with that id"
                                )
                        )
        );

        return new ResponseEntity<>(
                new CustomResponse<>(repo.save(screening)),
                HttpStatus.CREATED
        );
    }
}
