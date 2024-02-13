package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @PostMapping
    public ResponseEntity<Response> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        // 400 Bad request if not all fields present
        if (screening.getScreenNumber() <= 0 || screening.getCapacity() <= 0 || screening.getStartsAt() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a screening for the specified movie, " +
                    "please check all required fields are correct.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        // 404 Not found if no movie with given ID
        Movie movie = this.getMovie(id);
        if (movie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        screening.setMovie(movie);

        screening.setCreatedAt(ZonedDateTime.now());
        screening.setUpdatedAt(ZonedDateTime.now());

        // Response with created screening
        SuccessResponse response = new SuccessResponse();
        response.set(this.screeningRepository.save(screening));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response> getAllScreeningsForMovie(@PathVariable int id) {
        // 404 Not found if no movie with given ID
        Movie movie = this.getMovie(id);
        if (movie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        // List of screenings for movie, can be an empty list
        List<Screening> screenings = this.screeningRepository.findAll().stream()
                .filter(screening -> screening.getMovie() == movie).toList();

        // Response with list of screenings
        SuccessResponse response = new SuccessResponse();
        response.set(screenings);
        return ResponseEntity.ok(response);
    }

    // Method to find movie by id
    private Movie getMovie(int id) {
        return this.movieRepository.findById(id).orElse(null);
    }
}
