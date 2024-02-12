package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.utility.DataResponse;
import com.booleanuk.api.cinema.utility.ErrorResponse;
import com.booleanuk.api.cinema.utility.responses.ScreeningListResponse;
import com.booleanuk.api.cinema.utility.responses.ScreeningResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/movies/{id}/screenings")
    public ResponseEntity<DataResponse<?>> getAll(@PathVariable int id) {
        Movie findMovie = this.movieRepository.findById(id).orElse(null);
        if (findMovie == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No movie with that id");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        List<Screening> screenings = findMovie.getScreenings();
        ScreeningListResponse screeningListResponse = new ScreeningListResponse();
        screeningListResponse.set(screenings);
        return ResponseEntity.ok(screeningListResponse);
    }

    @PostMapping("/movies/{id}/screenings")
    public ResponseEntity<DataResponse<?>> create(@PathVariable int id, @RequestBody Screening screening) {
        Screening createScreening;
        try {
            screening.setCreatedAt(ZonedDateTime.now());
            screening.setUpdatedAt(ZonedDateTime.now());
            screening.setMovie(this.movieRepository.findById(id).orElseThrow(NullPointerException::new));
            createScreening = this.screeningRepository.save(screening);

        } catch (NullPointerException e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No movie with this id");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not create screening for movie");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        ScreeningResponse screeningResponse = new ScreeningResponse();
        screeningResponse.set(createScreening);
        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }
}
