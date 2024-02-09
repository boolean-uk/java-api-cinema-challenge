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

import java.util.List;

@RestController
@RequestMapping("screenings")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;


    @GetMapping
    public List<Screening> getAllScreenings() {
        return this.screeningRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screening> getScreeningByID(@PathVariable int id) {
        Screening screening = this.screeningRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        return ResponseEntity.ok(screening);
    }

    @PostMapping
    public ResponseEntity<Screening> createScreening(@RequestBody Screening screening) {
        Movie tempMov = this.movieRepository.findById(screening.getMovie().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie found"));
        screening.setMovie(tempMov);

        return ResponseEntity.ok(this.screeningRepository.save(screening));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Screening> deleteScreeningByID(@PathVariable int id) {
        Screening screening = this.screeningRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        this.screeningRepository.delete(screening);
        return ResponseEntity.ok(screening);


    }

    @PutMapping("/{id}")
    public ResponseEntity<Screening> updateScreening(@PathVariable int id, @RequestBody Screening screening) {

        Screening screeningToUpdate = this.screeningRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        Movie tempMov = this.movieRepository.findById(screening.getMovie().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie found"));
        screening.setMovie(tempMov);
        screeningToUpdate.setScreenNumber(screening.getScreenNumber());
        screeningToUpdate.setStartsAt(screening.getStartsAt());
        screeningToUpdate.setCapacity(screening.getCapacity());
        screeningToUpdate.setCreatedAt(screening.getCreatedAt());
        screeningToUpdate.setUpdatedAt(screening.getUpdatedAt());
        return new ResponseEntity<>(this.screeningRepository.save(screeningToUpdate), HttpStatus.CREATED);
    }

}
