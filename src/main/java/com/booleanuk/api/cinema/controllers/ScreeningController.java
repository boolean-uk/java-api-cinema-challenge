package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.ScreeningRequestDTO;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.payload.response.ScreeningListResponse;
import com.booleanuk.api.cinema.payload.response.ScreeningResponse;
import com.booleanuk.api.cinema.payload.response.ErrorResponse;
import com.booleanuk.api.cinema.payload.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<ScreeningListResponse> getAllScreenings() {
        ScreeningListResponse screeningListResponse = new ScreeningListResponse();
        screeningListResponse.set(this.screeningRepository.findAll());
        return ResponseEntity.ok(screeningListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createScreening(@PathVariable int id, @RequestBody ScreeningRequestDTO dto) {
        ScreeningResponse screeningResponse = new ScreeningResponse();
        try {
            Movie movie = movieRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Movie not found"));

            Screening screening = new Screening();
            screening.setScreenNumber(dto.screenNumber());
            screening.setCapacity(dto.capacity());
            screening.setMovie(movie);

            screeningResponse.set(screeningRepository.save(screening));
            return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}
