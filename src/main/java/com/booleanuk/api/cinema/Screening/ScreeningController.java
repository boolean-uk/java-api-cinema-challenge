package com.booleanuk.api.cinema.Screening;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.booleanuk.api.cinema.Movie.Movie;


import java.util.List;

@RestController
@RequestMapping("/movies/{id}/screenings")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @PostMapping
    public ResponseEntity<Screening> createScreening(@PathVariable Long id, @RequestBody Screening screening) {
        // Set the movie ID for the screening
        screening.setId(id);
        Screening createdScreening = screeningRepository.save(screening);
        return new ResponseEntity<>(createdScreening, HttpStatus.CREATED);
    }



    @GetMapping
    public ResponseEntity<List<Screening>> getAllScreenings(@PathVariable Long id) {
        List<Screening> screenings = screeningRepository.findAllByMovieId(id);
        return new ResponseEntity<>(screenings, HttpStatus.OK);
    }
}
