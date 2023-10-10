package com.booleanuk.api.cinema.screening;

import com.booleanuk.api.cinema.movie.Movie;
import com.booleanuk.api.cinema.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<Screening> getAllScreenings(@PathVariable int id) {
        return this.screeningRepository.findByMovieId(id);
    }

    @PostMapping
    public ResponseEntity<Screening> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        if (screening.getScreenNumber() == 0 || screening.getCapacity() == 0 || screening.getStartsAt() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields are invalid");
        } else {
            Screening alr = this.screeningRepository.findByScreenNumber(screening.getScreenNumber());
            if (alr != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Screening already exists");
            } else {
                Movie movie = this.movieRepository.findById(id).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));

                Date today = new Date();
                screening.setCreatedAt(today);
                screening.setUpdatedAt(today);
                screening.setMovie(movie);
            }
            return new ResponseEntity<>(this.screeningRepository.save(screening), HttpStatus.CREATED);
        }
    }
}
