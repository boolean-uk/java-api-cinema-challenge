package com.booleanuk.api.cinema.library.controllers;

import com.booleanuk.api.cinema.library.models.Movie;
import com.booleanuk.api.cinema.library.models.Screening;
import com.booleanuk.api.cinema.library.repository.MovieRepository;
import com.booleanuk.api.cinema.library.repository.ScreeningRepository;
import com.booleanuk.api.cinema.library.payload.request.ScreeningRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies/{movieId}/screenings")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<List<Screening>> getScreeningsByMovie(@PathVariable Integer movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        return ResponseEntity.ok(movie.getScreenings());
    }

    @PostMapping
    public ResponseEntity<Screening> createScreening(
            @PathVariable Integer movieId,
            @RequestBody ScreeningRequest request) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Screening screening = Screening.builder()
                .movie(movie)
                .screenNumber(request.getScreenNumber())
                .capacity(request.getCapacity())
                .startsAt(request.getStartsAt())
                .build();

        Screening savedScreening = screeningRepository.save(screening);

        return ResponseEntity.status(201).body(savedScreening);
    }
}
