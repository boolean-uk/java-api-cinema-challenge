package com.booleanuk.api.cinema.library.controllers;

import com.booleanuk.api.cinema.library.models.Movie;
import com.booleanuk.api.cinema.library.models.Screening;
import com.booleanuk.api.cinema.library.payload.request.ScreeningRequest;
import com.booleanuk.api.cinema.library.payload.response.ScreeningResponse;
import com.booleanuk.api.cinema.library.repository.MovieRepository;
import com.booleanuk.api.cinema.library.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies/{movieId}/screenings")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<List<ScreeningResponse>> getScreeningsByMovie(@PathVariable Integer movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        List<ScreeningResponse> responses = movie.getScreenings().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<ScreeningResponse> createScreening(
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

        Screening saved = screeningRepository.save(screening);

        return ResponseEntity.status(201).body(toResponse(saved));
    }

    private ScreeningResponse toResponse(Screening screening) {
        return ScreeningResponse.builder()
                .id(screening.getId())
                .screenNumber(screening.getScreenNumber())
                .capacity(screening.getCapacity())
                .startsAt(screening.getStartsAt())
                .createdAt(screening.getCreatedAt())
                .updatedAt(screening.getUpdatedAt())
                .build();
    }
}
