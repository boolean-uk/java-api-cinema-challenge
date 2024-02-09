package com.booleanuk.api.cinema.screening;

import com.booleanuk.api.cinema.movie.Movie;
import com.booleanuk.api.cinema.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;

    @PostMapping
    public ResponseEntity<Screening> createScreening(@RequestBody Screening screening) {
        validateScreeningOrThrowException(screening);

        Movie tempMovie = this.movieRepository
                .findById(screening.getMovie().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id found."));

        screening.setMovie(tempMovie);

        Screening newScreening = this.screeningRepository.save(screening);

        return new ResponseEntity<>(newScreening, HttpStatus.CREATED);
    }

    @GetMapping("/{movie_id}")
    public ResponseEntity<List<Screening>> getAllScreeningsForOneMovie(@PathVariable int movie_id) {
        List<Screening> screeningsForOneMovie = new ArrayList<>();

        List<Screening> allScreenings = this.screeningRepository.findAll();

        for(Screening screening : allScreenings) {
            if(screening.getMovie().getId() == movie_id) {
                screeningsForOneMovie.add(screening);
            }
        }

        return ResponseEntity.ok(screeningsForOneMovie);
    }

    private void validateScreeningOrThrowException(Screening screening) {
        if(screening.getScreenNumber() < 0 || screening.getCapacity() < 0 || screening.getStartsAt() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create a screening for the specified movie, please check all fields are correct.");
        }
    }
}
