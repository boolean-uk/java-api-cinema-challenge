package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.payload.response.ScreeningListResponse;
import com.booleanuk.api.cinema.payload.response.ScreeningResponse;
import com.booleanuk.api.cinema.payload.response.ErrorResponse;
import com.booleanuk.api.cinema.payload.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<ScreeningListResponse> getAllScreenings() {
        ScreeningListResponse screeningListResponse = new ScreeningListResponse();
        screeningListResponse.set(this.screeningRepository.findAll());
        return ResponseEntity.ok(screeningListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createScreening(@RequestBody Screening screening) {
        ScreeningResponse screeningResponse = new ScreeningResponse();
        try {
            screeningResponse.set(this.screeningRepository.save(screening));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getScreeningById(@PathVariable int id) {
        Screening screening = this.screeningRepository.findById(id).orElse(null);
        if (screening == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ScreeningResponse screeningResponse = new ScreeningResponse();
        screeningResponse.set(screening);
        return ResponseEntity.ok(screeningResponse);
    }
}
