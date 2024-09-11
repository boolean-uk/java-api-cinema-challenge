package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.repositories.TicketRepository;
import com.booleanuk.api.cinema.respons_handling.Message;
import com.booleanuk.api.cinema.respons_handling.ResponseCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<ResponseCreator<?>> getAll() {
        return ResponseEntity.ok(new ResponseCreator<>("success", this.movieRepository.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseCreator<?>> getById(@PathVariable("id") Integer id) {
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie == null) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("Movie not found")) , HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new ResponseCreator<>("success", movie));
    }

    @PostMapping
    public ResponseEntity<ResponseCreator<?>> create(@RequestBody Movie movie) {
        Movie createdMovie = this.movieRepository.save(movie);
        if (movie == null) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("At least one non-null field is null")) , HttpStatus.BAD_REQUEST);
        }
        System.out.println(movie);
        List<Screening> screenings = movie.getScreenings();
        if (screenings != null) {
            for (Screening screening : screenings) {
                List<Screening> screeningsAtScreen = this.screeningRepository.findByScreenNumber(screening.getScreenNumber());
                for (Screening screeningAtScreen : screeningsAtScreen) {
                    if ((screeningAtScreen.getStartsAt().isBefore(screening.getStartsAt()) && screeningAtScreen.getStartsAt().plusMinutes(movie.getRuntimeMins()).isAfter(screening.getStartsAt())) ||
                            (screening.getStartsAt().isBefore(screeningAtScreen.getStartsAt()) && screening.getStartsAt().plusMinutes(movie.getRuntimeMins()).isAfter(screeningAtScreen.getStartsAt())) ||
                            screening.getStartsAt().isEqual(screeningAtScreen.getStartsAt())) {
                        return new ResponseEntity<>(new ResponseCreator<>("error", new Message("bad request")), HttpStatus.BAD_REQUEST);
                    }
                }
                screening.setMovie(createdMovie);
                this.screeningRepository.save(screening);
            }
        }
        return new ResponseEntity<>(new ResponseCreator<>("success", createdMovie), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseCreator<?>> update(@PathVariable("id") Integer id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.movieRepository.findById(id).orElse(null);
        if (movieToUpdate == null) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("Movie not found")) , HttpStatus.NOT_FOUND);
        }

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());

        movieToUpdate = this.movieRepository.save(movieToUpdate);
        if (movieToUpdate == null) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("At least one non-null field is null")) , HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ResponseCreator<>("success", movieToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseCreator<?>> deleteUser(@PathVariable("id") Integer id) {
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie == null) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("Movie not found")) , HttpStatus.NOT_FOUND);
        }
        if (!movie.getScreenings().isEmpty()) {
            return new ResponseEntity<>(new ResponseCreator<>("error", new Message("Delete all screenings connected to the movie first")) , HttpStatus.BAD_REQUEST);
        }
        this.movieRepository.delete(movie);
        return ResponseEntity.ok(new ResponseCreator<>("success", movie));
    }
}
