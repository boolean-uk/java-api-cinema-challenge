package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @PostMapping
    public ResponseEntity<Screening> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        this.checkHasRequiredFields(screening);
        screening.setMovie(this.checkMovieExists(id));
        screening.setCreatedAt(new Date());
        screening.setUpdatedAt(new Date());
        return new ResponseEntity<>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Screening> getAllScreeningsForMovie(@PathVariable int id) {
        this.checkMovieExists(id);
        return this.screeningRepository.findAll().stream().filter(screening -> screening.getMovie().getId() == id).toList();
    }

    // Method to check if all required fields are contained in the request, used in createScreening()
    private void checkHasRequiredFields(Screening screening) {
        if (screening.getScreenNumber() <= 0 || screening.getCapacity() <= 0 || screening.getStartsAt() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create a screening for the specified movie, please check all required fields are correct.");
        }
    }

    // Method to find movie by id
    private Movie checkMovieExists(int id) {
        return this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id found."));
    }
}
