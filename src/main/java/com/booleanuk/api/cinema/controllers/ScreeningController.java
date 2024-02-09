package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Response;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.stream.Stream;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;

    public ScreeningController(ScreeningRepository screeningRepository, MovieRepository movieRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
    }

    @PostMapping
    public ResponseEntity<Response<?>> add(@PathVariable int id, @RequestBody Screening screening) {
        if (this.movieRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.NOT_FOUND);
        }
        if (!isValidObject(screening)) {
            return ResponseEntity.badRequest().body(Response.BAD_REQUEST);
        }

        Movie movie = this.movieRepository.findById(id).get();
        screening.setMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.success(this.screeningRepository.save(screening)));
    }

    @GetMapping
    public ResponseEntity<Response<?>> getAll(@PathVariable int id) {
        if (this.movieRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.NOT_FOUND);
        }
        return ResponseEntity.ok(Response.success(screeningRepository.findAllByMovieId(id)));
    }

    private boolean isValidObject(Screening screening) {
        return Stream.of(screening.getScreenNumber(), screening.getCapacity(), screening.getStartsAt())
                .noneMatch(Objects::isNull);
    }
}
