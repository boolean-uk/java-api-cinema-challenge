package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.*;
import com.booleanuk.api.cinema.response.screening.ScreeningListResponse;
import com.booleanuk.api.cinema.response.screening.ScreeningResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("movies/{id}/screenings")
    public ResponseEntity<Response<?>> getEveryScreeningListForSpecifiedMovie(@PathVariable (name = "id")  int id) {
        List<Screening> screeningsForMovie = screeningRepository.findByMovie_Id(id);
        if (screeningsForMovie.isEmpty() || screeningsForMovie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No screenings for movies matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ScreeningListResponse screeningListResponse = new ScreeningListResponse();
        screeningListResponse.set(screeningsForMovie);
        return ResponseEntity.ok(screeningListResponse);
    }

    @PostMapping("movies/{id}/screenings")
    public ResponseEntity<Response<?>> createScreeningForExistingMovie(@PathVariable (name = "id") int id ,@RequestBody Screening screening) {
        if (screening.getCapacity() < 0 || screening.getScreenNumber() < 0 || screening.getStartsAt() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a screening for the specified movie, please check all fields are correct");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Movie tempMovie = this.movieRepository.findById(id).orElse(null);

        if(tempMovie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No movies matching that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        screening.setMovie(tempMovie);
        Screening createdScreening = this.screeningRepository.save(screening);

        ScreeningResponse screeningResponse = new ScreeningResponse();
        screeningResponse.set(createdScreening);
        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }
}
