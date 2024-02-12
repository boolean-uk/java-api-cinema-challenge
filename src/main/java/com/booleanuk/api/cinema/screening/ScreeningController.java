package com.booleanuk.api.cinema.screening;

import com.booleanuk.api.cinema.movie.Movie;
import com.booleanuk.api.cinema.movie.MovieRepository;
import com.booleanuk.api.cinema.response.*;
import com.booleanuk.api.cinema.response.Error;
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
    public ResponseEntity<Response> getScreening(@PathVariable int id) {
        Movie movie = this.movieRepository
                .findById(id)
                .orElse(null);
        if (movie == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Movie not found")), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ScreeningListResponse(movie.getScreeningList()), HttpStatus.OK);
    }

    @PostMapping("{id}/screenings")
    public ResponseEntity<Response> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        Movie movie = this.movieRepository
                .findById(id)
                .orElse(null);
        if (movie == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Movie not found")), HttpStatus.NOT_FOUND);
        }
        screening.setCreatedAt(currentTime);
        screening.setUpdatedAt(currentTime);
        screening.setMovie(screening.getMovie());
        return new ResponseEntity<>(new ScreeningResponse(screening), HttpStatus.OK);
    }

}
