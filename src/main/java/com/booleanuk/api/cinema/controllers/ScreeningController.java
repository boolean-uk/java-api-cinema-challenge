package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Customer;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;

    private LocalDateTime today;
    private DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm");

    @GetMapping
    public List<Screening> findAll(@PathVariable int id){
        Movie tempMovie = findTheMovie(id);
        return tempMovie.getScreenings();
    }


    @PostMapping
    public ResponseEntity<Screening> addScreening(@PathVariable int id,@RequestBody Screening screening){
        today = LocalDateTime.now();

        Movie tempMovie = findTheMovie(id);

        screening.setMovie(tempMovie);
        screening.setCreatedAt(today.format(pattern));


       return new ResponseEntity<Screening>(this.screeningRepository.save(screening),
               HttpStatus.CREATED);
    }



    private Movie findTheMovie(int id){
        return this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
