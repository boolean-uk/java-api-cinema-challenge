package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.utility.DataResponse;
import com.booleanuk.api.cinema.utility.ErrorResponse;
import com.booleanuk.api.cinema.utility.Responses.ScreeningListResponse;
import com.booleanuk.api.cinema.utility.Responses.ScreeningResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class ScreeningController {
    @Autowired
    private ScreeningRepository screenings;
    @Autowired
    private MovieRepository movies;

    @PostMapping("/movies/{id}/screenings")
    public ResponseEntity<DataResponse<?>> createScreening(@PathVariable int id, @RequestBody Screening screening){
        Screening screeningToCreate;
        try {
            screening.setCreatedAt(ZonedDateTime.now());
            screening.setUpdatedAt(ZonedDateTime.now());
            screening.setMovie(this.movies.findById(id).orElseThrow(NullPointerException::new));
            screeningToCreate = this.screenings.save(screening);
        } catch (NullPointerException e){
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a screening for the specified movie, please check all fields are correct.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        ScreeningResponse response = new ScreeningResponse();
        response.set(screeningToCreate);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/movies/{id}/screenings")
    public ResponseEntity<DataResponse<?>> getAllScreenings(@PathVariable int id) {
        Movie movieToFind = this.movies.findById(id).orElse(null);
        if (movieToFind == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        List<Screening> screeningsToFind = movieToFind.getScreenings();
        ScreeningListResponse response = new ScreeningListResponse();
        response.set(screeningsToFind);
        return ResponseEntity.ok(response);
    }
}
