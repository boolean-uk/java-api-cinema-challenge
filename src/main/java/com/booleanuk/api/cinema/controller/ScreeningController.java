package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.CustomResponse;
import com.booleanuk.api.cinema.response.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;


    @GetMapping("/{movie_id}/screenings")
    public ResponseEntity<CustomResponse> getAllScreenings(@PathVariable int movie_id) {
        if (screeningRepository.findByMovieId(movie_id).isEmpty()) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("No data found"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }
        Movie movie = this.movieRepository.findById(movie_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));

        return ResponseEntity.ok(new CustomResponse("success", movie.getScreenings()));
    }


    @PostMapping("/{movie_id}/screenings")
    public ResponseEntity<CustomResponse> createScreening(@PathVariable int movie_id, @RequestBody Screening screening) {
        if (screening.getMovie() == null || screening.getScreenNumber() == 0 || screening.getStartsAt() == null || screening.getCapacity() == 0) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Check if all fields are correct!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }
        Movie tempMov = this.movieRepository.findById(screening.getMovie().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie found"));
        screening.setMovie(tempMov);

        return ResponseEntity.ok(new CustomResponse("success", this.screeningRepository.save(screening)));
    }


}
