package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/movies")
public class MovieController extends GenericController<Movie, Integer> {
    @Autowired
    private MovieRepository repository;
    @Autowired
    private ScreeningController screeningController;

    @Override
    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie) {
        try {
            Movie newMovie = repository.save(movie);
            if (!movie.getScreenings().isEmpty()) {
                for(Screening screening : movie.getScreenings()) {
                    screening.setMovie(newMovie);
                    screeningController.createScreening(newMovie.getId(), screening);
                }
            }
            return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid movie data");
        }
    }
}
