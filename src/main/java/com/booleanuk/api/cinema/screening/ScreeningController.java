package com.booleanuk.api.cinema.screening;

import com.booleanuk.api.cinema.movie.Movie;
import com.booleanuk.api.cinema.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("movies")
public class ScreeningController {
    LocalDateTime currentTime = LocalDateTime.now();

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

//    @GetMapping
//    public List<Movie> getAll() {
//        return this.movieRepository.findAll();
//    }

    @GetMapping("{id}/screenings")
    public List<Screening> getScreening(@PathVariable int id) {
        Movie movie = this.movieRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        return movie.getScreeningList();
    }

    @PostMapping("{id}/screenings")
    public ResponseEntity<Screening> createScreening(@PathVariable Movie id, @RequestBody Screening screening) {
        screening.setCreatedAt(currentTime);
        screening.setUpdatedAt(currentTime);
        screening.setMovie(id);
        return new ResponseEntity<>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }

}
