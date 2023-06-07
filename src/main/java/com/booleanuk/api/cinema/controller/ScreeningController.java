package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.DateTimeGenerator;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.script.ScriptEngine;
import java.util.List;

@RestController
@RequestMapping("screenings")
public class ScreeningController {

    @Autowired
    ScreeningRepository screeningRepository;

    public ScreeningController() {}

    @GetMapping
    public List<Screening> getAll() {
        return this.screeningRepository.findAll();
    }

    @GetMapping("/{id}")
    public Screening getById(@PathVariable("id") Integer id) {
        return this.screeningRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public ResponseEntity<Screening> createScreening(@RequestBody Screening screening) {
        screening.setCreatedAt(DateTimeGenerator.getDateTimeNow());
        screening.setUpdatedAt(screening.getCreatedAt());
        return new ResponseEntity<Screening>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Screening> updateScreening(@PathVariable Integer id, @RequestBody Screening screening) {
        Screening screeningToUpdate = this.screeningRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        screeningToUpdate.setScreenNumber(screening.getScreenNumber());
        screeningToUpdate.setCapacity(screening.getCapacity());
        screeningToUpdate.setStartsAt(screening.getStartsAt());
        screeningToUpdate.setUpdatedAt(DateTimeGenerator.getDateTimeNow());

        return new ResponseEntity<Screening>(this.screeningRepository.save(screeningToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Screening> deleteScreening(@PathVariable Integer id) {
        Screening screeningToDelete = this.screeningRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        this.screeningRepository.delete(screeningToDelete);
        return ResponseEntity.ok(screeningToDelete);
    }
}
