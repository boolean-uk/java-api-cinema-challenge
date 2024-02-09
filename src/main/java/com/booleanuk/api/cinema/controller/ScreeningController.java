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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;
    private LocalDateTime time = LocalDateTime.now();

    @GetMapping
    public List<Screening> getAllScreenings() {
        return this.screeningRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screening> getAScreening(@PathVariable int id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(screening);
    }

    @PostMapping
    public ResponseEntity<Screening> createScreening(@RequestBody Screening screening) {
        screening.setCreatedAt(time);
        screening.setUpdatedAt(time);
        Screening newScreening = this.screeningRepository.save(screening);
        newScreening.setTickets(new ArrayList<>());
        return new ResponseEntity<>(screening, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Screening> updateScreening(@PathVariable int id, @RequestBody Screening screening) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie was not found"));
        Screening screeningToUpdate = screeningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        screeningToUpdate.setMovie(screening.getMovie());
        screeningToUpdate.setScreenNumber(screening.getScreenNumber());
        screeningToUpdate.setStartsAt(screening.getStartsAt());
        screeningToUpdate.setCapacity(screening.getCapacity());
        screeningToUpdate.setUpdatedAt(LocalDateTime.now());
        return new ResponseEntity<>(this.screeningRepository.save(screeningToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Screening> deleteScreening(@PathVariable int id) {
        Screening screeningToDelete = screeningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        if(!screeningToDelete.getTickets().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Screening has tickets, and could not be deleted");
        }
        this.screeningRepository.delete(screeningToDelete);
        screeningToDelete.setTickets(new ArrayList<>());
        return ResponseEntity.ok(screeningToDelete);
    }
}
