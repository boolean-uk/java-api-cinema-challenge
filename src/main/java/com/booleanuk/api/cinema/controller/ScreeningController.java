package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.ApiResponse;
import com.booleanuk.api.cinema.exception.NotFoundException;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies/{movieId}/screenings")
public class ScreeningController {
    private static final String SUCCESS = "success";
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public ScreeningController(ScreeningRepository screeningRepository, MovieRepository movieRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<Screening>> getAll(@PathVariable int movieId) {
        Movie movie = this.movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            throw new NotFoundException("not found");
        }
        return new ApiResponse<>(SUCCESS, this.screeningRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Screening> create(@PathVariable int movieId, @Valid @RequestBody Screening body) {
        return this.movieRepository.findById(movieId)
                .map(movie -> {
                    body.setMovie(movie);
                    this.screeningRepository.save(body);
                    return new ApiResponse<>(SUCCESS, body);
                })
                .orElseThrow(() -> new NotFoundException("not found"));
    }
}
