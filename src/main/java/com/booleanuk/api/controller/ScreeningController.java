package com.booleanuk.api.controller;

import com.booleanuk.api.model.Movie;
import com.booleanuk.api.model.Screening;
import com.booleanuk.api.repository.MovieRepository;
import com.booleanuk.api.repository.ScreeningRepository;
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
    public List<Screening> getAllScreenings(@PathVariable int movie_id) {
        checkIfMovieExists(movie_id);
        return this.screeningRepository.findAll();
    }

    @PostMapping("/{movie_id}/screenings")
    public ResponseEntity<Screening> createScreening(@PathVariable int movie_id,
                                                     @RequestBody Screening screening) {
        checkAllScreeningFields(screening);
        Screening createdScreening = this.screeningRepository.save(screening);
        Movie tempMovie = getMovieWithOrFound(movie_id);
        createdScreening.setMovie(tempMovie);
        createdScreening.setTickets(new ArrayList<>());
        return new ResponseEntity<>(createdScreening, HttpStatus.CREATED);
    }

    @DeleteMapping("/{movie_id}/screenings/{id}")
    public ResponseEntity<Screening> deleteScreening(@PathVariable int movie_id,
                                                     @PathVariable int id) {
        checkIfMovieExists(movie_id);
        Screening screeningToDelete = getScreeningOrNotFound(id);
        try {
            this.screeningRepository.delete(screeningToDelete);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Screening is a foreign key to a Ticket");
        }
        screeningToDelete.setTickets(new ArrayList<>());
        return ResponseEntity.ok(screeningToDelete);
    }

    //--------------------------- Private section---------------------------//

    private Screening getScreeningOrNotFound(int id) {
        return this.screeningRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
    }

    private Movie getMovieWithOrFound(int id) {
        return this.movieRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No movie with that ID"));
    }

    private void checkIfMovieExists(int id) {
        try {
            this.movieRepository.findById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that ID");
        }
    }

    private void checkAllScreeningFields(Screening screening) {
        if (screening.getScreenNumber() == 0 ||
        screening.getCapacity() == 0 ||
        screening.getStartsAt() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Bad data in RequestBody");
        }
    }
}
