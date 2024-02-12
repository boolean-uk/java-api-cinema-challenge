package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.enums.Rating;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Response;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("movies")
public class MovieController {
    private final MovieRepository movieRepository;
    private final ScreeningRepository screeningRepository;

    public MovieController(MovieRepository movieRepository, ScreeningRepository screeningRepository) {
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
    }

    @PostMapping
    public ResponseEntity<Response<?>> add(@RequestBody Movie movie) {
        // Check if movie is valid
        if (!isValidObject(movie)) {
            return ResponseEntity.badRequest().body(Response.BAD_REQUEST);
        }
        // Save movie to DB
        this.movieRepository.save(movie);
        // Set the movie for the screenings to the recently saved movie object
        // Then add screenings to DB
        for (Screening screenings : movie.getScreenings()) {
            screenings.setMovie(movie);
        }
        List<Screening> screenings = screeningRepository.saveAll(movie.getScreenings());
        // Set screenings to the movie object we return
        // Return the movie with screenings
        movie.setScreenings(screenings);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success(movie));
    }

    @GetMapping
    public Response<List<Movie>> getAll() {
        return Response.success(this.movieRepository.findAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody Movie movie) {
        if (!isValidObject(movie)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.BAD_REQUEST);
        }
        if (this.movieRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.NOT_FOUND);
        }

        Movie movieToUpdate = this.movieRepository.findById(id).get();
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        movieToUpdate.setUpdatedAt(ZonedDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.success(this.movieRepository.save(movieToUpdate)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteById(@PathVariable int id) {
        if (this.movieRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.NOT_FOUND);
        }

        Movie movieToDelete = this.movieRepository.findById(id).get();
        try {
            this.movieRepository.delete(movieToDelete);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error("violation of foreign key constraint"));
        }

        return ResponseEntity.ok(Response.success(movieToDelete));
    }

    private boolean isValidObject(Movie movie) {
        if (Stream.of(movie.getTitle(), movie.getRating(), movie.getDescription(), movie.getRuntimeMins(), movie.getScreenings())
                .anyMatch(Objects::isNull)) {
            return false;
        }
        return Rating.isValidRating(movie.getRating());
    }
}
