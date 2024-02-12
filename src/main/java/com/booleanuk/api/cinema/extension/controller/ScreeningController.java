package com.booleanuk.api.cinema.extension.controller;

import com.booleanuk.api.cinema.extension.model.Movie;
import com.booleanuk.api.cinema.extension.model.Screening;
import com.booleanuk.api.cinema.extension.repository.MovieRepository;
import com.booleanuk.api.cinema.extension.repository.ScreeningRepository;
import com.booleanuk.api.cinema.extension.response.CustomResponse;
import com.booleanuk.api.cinema.extension.response.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/{id}/screenings")
    public ResponseEntity<CustomResponse> getScreeningsByMovieId(@PathVariable("id") Long id) {
        if(!movieRepository.existsById(id)) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }

        Movie movie = movieRepository
                .findById(id).get();

        List<Screening> customResponses = new ArrayList<>(movie.getScreenings());
        CustomResponse customResponse2 = new CustomResponse("success", customResponses);

        return new ResponseEntity<>(customResponse2, HttpStatus.OK);
    }

    @PostMapping("/{id}/screenings")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomResponse> createScreening(@PathVariable("id") Long id, @RequestBody Screening screening) {
        if(!movieRepository.existsById(id)) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }

        if (screening.getScreenNumber() == null || screening.getScreenNumber() <= 0){
            return new ResponseEntity<>(new CustomResponse("error", new Error("Screening must have a screen number")), HttpStatus.BAD_REQUEST);
        } else if (screening.getCapacity() == null || screening.getCapacity() <= 0) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("Screening must have a capacity")), HttpStatus.BAD_REQUEST);
        }
        Movie movie = movieRepository
                .findById(id).get();
        screening.setMovie(movie);

        Screening newScreening = screeningRepository.save(screening);
        return new ResponseEntity<>(new CustomResponse("success", newScreening), HttpStatus.CREATED);
    }
}
