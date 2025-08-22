package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController extends GenericController<Movie, Integer> {
    @Autowired
    private MovieRepository repository;
    @Autowired
    private ScreeningController screeningController;

    @Override
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Movie movie) {
        Response<Object> response = new Response<>();
        try {
            Movie newMovie = repository.save(movie);
            if (!movie.getScreenings().isEmpty()) {
                for (Screening screening : movie.getScreenings()) {
                    screening.setMovie(newMovie);
                    screeningController.createScreening(newMovie.getId(), screening);
                }
            }
            response.setSuccess(newMovie);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setError(e.toString());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
