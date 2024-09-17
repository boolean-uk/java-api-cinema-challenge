package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.responses.ScreeningResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("movies")
public class ScreeningController {
    @Autowired
    private ScreeningRepository repository;

    @Autowired
    private MovieRepository associatedMovieRepository;

    @PostMapping("{id}/screenings")
    public ResponseEntity<Response<?>> create(
            @PathVariable int id,
            @RequestBody Screening screening)
    {
        Screening newScreening = this.repository.save(screening);

        // Get movie
        Movie movie = this.associatedMovieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with this id not found.")
        );

        // Associate the movie with this screening
        newScreening.setMovie(movie);
        // Update screening list for the movie
        movie.getScreenings().add(newScreening);
        this.associatedMovieRepository.save(movie);

        // Create response object
        ScreeningResponse response = new ScreeningResponse();
        response.set(newScreening);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("{id}/screenings")
    public ResponseEntity<List<Screening>> getAll(@PathVariable int id) {
        return ResponseEntity.ok(this.repository.findAllByMovieId(id));
    }
}
