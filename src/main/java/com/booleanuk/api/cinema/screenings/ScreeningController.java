package com.booleanuk.api.cinema.screenings;

import com.booleanuk.api.cinema.movies.Movie;
import com.booleanuk.api.cinema.movies.MovieRepository;
import jakarta.validation.Valid;
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
    public List<Screening> getAll(@PathVariable int id){
        return this.screeningRepository.findByMovieId(id);
    }

    @PostMapping
    public ResponseEntity<Screening> addOne(@PathVariable int id, @Valid @RequestBody Screening screening){
        Movie movie = this.movieRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        screening.setMovie(movie);

        return new ResponseEntity<>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }
}
