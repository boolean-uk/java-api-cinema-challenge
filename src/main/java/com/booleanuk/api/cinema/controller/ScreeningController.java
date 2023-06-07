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
import java.util.Optional;

@RestController
@RequestMapping("movies/")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("{id}/screenings")
    public ResponseEntity<List<Screening>> getAllScreenings(@PathVariable int id){
        Movie movieToSearch = this.movieRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Movie not found"));
        List<Screening> screenings = screeningRepository.findByMovieId(movieToSearch.getId());

        return new ResponseEntity<>(screenings, HttpStatus.OK);
    }
    @PostMapping("{id}/screenings")

    public ResponseEntity<Screening> createScreeningForMovie(@PathVariable int id, @RequestBody Screening screening) {
        // Retrieve the movie by its ID
        Optional<Movie> optionalMovie = movieRepository.findById(id);

        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            screening.setMovie(movie);
            screening.setCreatedAt(LocalDateTime.now());
            screening.setUpdatedAt(LocalDateTime.now());
            Screening savedScreening = screeningRepository.save(screening);
            return new ResponseEntity<>(savedScreening, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
