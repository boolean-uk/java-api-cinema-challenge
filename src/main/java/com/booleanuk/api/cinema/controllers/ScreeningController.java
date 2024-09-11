package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.respons_handling.Message;
import com.booleanuk.api.cinema.respons_handling.ResponseCreator;
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
    public ResponseEntity<ResponseCreator<?>> getAll(@PathVariable("id") Integer id) {
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie == null) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("Movie not found")) , HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new ResponseCreator<List<Screening>>("success", movie.getScreenings()));
    }

    @PostMapping
    public ResponseEntity<ResponseCreator<?>> createScreening(@PathVariable("id") Integer id, @RequestBody Screening screening) {

        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie == null) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("Movie not found")) , HttpStatus.NOT_FOUND);
        }

        List<Screening> screeningsAtScreen = this.screeningRepository.findByScreenNumber(screening.getScreenNumber());
        for (Screening screeningAtScreen : screeningsAtScreen) {
            if ((screeningAtScreen.getStartsAt().isBefore(screening.getStartsAt()) && screeningAtScreen.getStartsAt().plusMinutes(movie.getRuntimeMins()).isAfter(screening.getStartsAt()) )||
                    (screening.getStartsAt().isBefore(screeningAtScreen.getStartsAt()) && screening.getStartsAt().plusMinutes(movie.getRuntimeMins()).isAfter(screeningAtScreen.getStartsAt())) ||
                        screening.getStartsAt().isEqual(screeningAtScreen.getStartsAt())) {
                return new ResponseEntity<>(new ResponseCreator<>("error", new Message("bad request")) , HttpStatus.BAD_REQUEST);
            }
        }

        screening.setMovie(movie);
        return new ResponseEntity<>(new ResponseCreator<>("success", this.screeningRepository.save(screening)), HttpStatus.CREATED);
    }
}
