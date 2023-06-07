package com.booleanuk.api.cinema.controllers;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("movies/{movieId}/screenings")
public class ScreeningController {
    @Autowired
    ScreeningRepository screeningRepository;
    @Autowired
    MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Screening>>> getAll(@PathVariable int movieId) {
        List<Screening> screenings = screeningRepository.findByMovieId(movieId);
        ApiResponse<List<Screening>> response = new ApiResponse<>("success", screenings);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Screening>> createScreening(@PathVariable int movieId, @RequestBody Screening screening){
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with provided id found."));
        screening.setMovie(movie);
        Screening createdScreening = screeningRepository.save(screening);
        ApiResponse<Screening> response = new ApiResponse<>("success", createdScreening);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
