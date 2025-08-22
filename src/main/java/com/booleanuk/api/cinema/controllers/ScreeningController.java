package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<List<Screening>> getAll(@PathVariable int id){
        if (!movieRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
        List<Screening> screeningsToGet = screeningRepository.findByMovieId(id);
        return ResponseEntity.ok(screeningsToGet);
    }

    @PostMapping
    public ResponseEntity<Screening> createScreening(@PathVariable int id, @RequestBody Screening screening){
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        screening.setMovie(movie);
        return new ResponseEntity<Screening>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }

//    @PutMapping("{id}")
//    public ResponseEntity<Screening> updateScreening(@PathVariable int id, @RequestBody Screening screening){
//        Screening screeningToUpdate = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
//
//        screeningToUpdate.setScreenNumber(screening.getScreenNumber());
//        screeningToUpdate.setCapacity(screening.getCapacity());
//        screeningToUpdate.setStartsAt(screening.getStartsAt());
//
//        return new ResponseEntity<>(this.repository.save(screeningToUpdate), HttpStatus.CREATED);
//    }
}
