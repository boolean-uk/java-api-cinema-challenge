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
import java.util.List;

@RestController
@RequestMapping("movies/{movie_id}/screenings")
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
    public ResponseEntity<Screening> getScreeningById(@PathVariable int id) {
        Screening screening = this.screeningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scerrening Not Found by ID"));
        return ResponseEntity.ok(screening);
    }

    @PostMapping
    public ResponseEntity<Screening> createScreening(@RequestBody Screening screening) {
        Movie movieForScreening = this.movieRepository.findById(screening.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie Not Found by ID"));
        screening.setMovie(movieForScreening);
        screening.setCreatedAt(LocalDateTime.now());

        return ResponseEntity.ok(this.screeningRepository.save(screening));
    }

//    @PutMapping
//    public ResponseEntity<Screening> updateScreening(@PathVariable int id, @RequestBody Screening screening) {
//        Screening screeningToUpdate = this.screeningRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening Not Found by ID"));
//        screeningToUpdate.setScreenNumber(screening.getScreenNumber());
//        screeningToUpdate.setCapacity(screening.getCapacity());
//        screeningToUpdate.setStartsAt(screening.getStartsAt());
//
//        screeningToUpdate.updateScreening();
//
//          ...
//    }



}
