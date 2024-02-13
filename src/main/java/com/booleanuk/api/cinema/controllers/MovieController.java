package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.enums.Rating;
import com.booleanuk.api.cinema.models.*;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.repositories.TicketRepository;
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
    private final TicketRepository ticketRepository;

    public MovieController(MovieRepository movieRepository, ScreeningRepository screeningRepository, TicketRepository ticketRepository) {
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
        this.ticketRepository = ticketRepository;
    }

    @PostMapping
    public ResponseEntity<Response<?>> add(@RequestBody Movie movie) {
        // Check if movie is valid and contains all required fields
        if (lacksRequiredFields(movie) || !Rating.isValidRating(movie.getRating())) {
            return ResponseEntity.badRequest().body(Response.BAD_REQUEST);
        }
        // Save movie to DB
        this.movieRepository.save(movie);
        // Set the movie for the screenings to the recently saved movie object
        // Then add screenings to DB
        for (Screening screenings : movie.getScreenings()) {
            screenings.setMovie(movie);
        }
        screeningRepository.saveAll(movie.getScreenings());
        // Return MovieDTO object (without screenings according to spec)
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.success(MovieDTO.fromMovie(movie)));
    }

    @GetMapping
    public Response<List<MovieDTO>> getAll() {
        return Response.success(MovieDTO.fromMovieList(this.movieRepository.findAll()));
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody Movie movie) {
        if (!Rating.isValidRating(movie.getRating())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.BAD_REQUEST);
        }
        if (this.movieRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.NOT_FOUND);
        }

        Movie movieToUpdate = this.movieRepository.findById(id).get();
        movieToUpdate.setTitle(movie.getTitle() != null ? movie.getTitle() : movieToUpdate.getTitle());
        movieToUpdate.setRating(movie.getRating() != null ? movie.getRating() : movieToUpdate.getRating());
        movieToUpdate.setDescription(movie.getDescription() != null ? movie.getDescription() : movieToUpdate.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins() != null ? movie.getRuntimeMins() : movieToUpdate.getRuntimeMins());
        movieToUpdate.setUpdatedAt(ZonedDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.success(MovieDTO.fromMovie(this.movieRepository.save(movieToUpdate))));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteById(@PathVariable int id) {
        if (this.movieRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.NOT_FOUND);
        }

        Movie movieToDelete = this.movieRepository.findById(id).get();
        if (!movieToDelete.getScreenings().isEmpty()) {
            for (Screening screening : movieToDelete.getScreenings()) {
                if (!screening.getTickets().isEmpty()) {
                    ticketRepository.deleteAll(screening.getTickets());
                }
                screeningRepository.delete(screening);
            }
        }
        try {
            this.movieRepository.delete(movieToDelete);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.error("violation of foreign key constraint"));
        }

        return ResponseEntity.ok(Response.success(MovieDTO.fromMovie(movieToDelete)));
    }

    private boolean lacksRequiredFields(Movie movie) {
        return Stream.of(movie.getTitle(), movie.getRating(), movie.getDescription(), movie.getRuntimeMins(), movie.getScreenings())
                .anyMatch(Objects::isNull);
    }
}
