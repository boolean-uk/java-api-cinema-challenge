package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.payload.response.*;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<ScreeningListResponse> getAll() {
        ScreeningListResponse screeningListResponse = new ScreeningListResponse();
        screeningListResponse.set(this.screeningRepository.findAll());
        return ResponseEntity.ok(screeningListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Screening screening) {
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

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody Screening screening) {
        Screening updated = this.screeningRepository.findById(id).orElse(null);
        if (updated == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        updated.setScreenNumber(screening.getScreenNumber());
        updated.setCapacity(screening.getCapacity());
        updated.setStartsAt(screening.getStartsAt());

        try {
            updated = this.screeningRepository.save(updated);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        ScreeningResponse screeningResponse = new ScreeningResponse();
        screeningResponse.set(updated);
        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id) {
        Screening delete = this.screeningRepository.findById(id).orElse(null);
        if (delete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.screeningRepository.delete(delete);
        ScreeningResponse screeningResponse = new ScreeningResponse();
        screeningResponse.set(delete);
        return ResponseEntity.ok(screeningResponse);
    }
}
