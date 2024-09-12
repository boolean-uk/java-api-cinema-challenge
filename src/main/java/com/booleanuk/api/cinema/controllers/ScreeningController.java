package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;

    @PostMapping
    public ResponseEntity<Screening> create(@RequestBody Screening screening, @PathVariable int id) {
        System.out.println("Test screening post");
        Movie movie = this.movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id")
        );
        screening.setMovie(movie);
        screening.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        screening.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return new ResponseEntity<>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Screening> getAllByMovieId(@PathVariable int id) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id")
        );
        List<Screening> result = new ArrayList<>();
        for (Screening s : screeningRepository.findAll()){
            if(s.getMovie().getId() == id){
                result.add(s);
            }
        }
        return result;
    }
/*
    @GetMapping
    public ResponseEntity<Screening> getOne(@PathVariable int id) {
        Screening screening = this.screeningRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with that id")
        );
        return ResponseEntity.ok(screening);
    }

    @PutMapping
    public ResponseEntity<Screening> update(@PathVariable int id, @RequestBody Screening screening) {
        Screening screeningToUpdate = this.screeningRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with that id")
        );
        screeningToUpdate.setMovieId(screening.getMovieId());
        screeningToUpdate.setScreenNumber(screening.getScreenNumber());
        screeningToUpdate.setStartsAt(screening.getStartsAt());
        screeningToUpdate.setCapacity(screening.getCapacity());
        screeningToUpdate.setUpdatedAt(LocalDateTime.now());
        return new ResponseEntity<>(this.screeningRepository.save(screeningToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Screening> delete(@PathVariable int id) {
        Screening screeningToDelete = this.screeningRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with that id")
        );
        this.screeningRepository.delete(screeningToDelete);
        return ResponseEntity.ok(screeningToDelete);
    }

 */
}
