package com.booleanuk.api.cinema.screenings;

import com.booleanuk.api.cinema.movies.Movie;
import com.booleanuk.api.cinema.movies.MovieRepository;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.responses.ScreeningListResponse;
import com.booleanuk.api.cinema.responses.ScreeningResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    private ErrorResponse errorResponse = new ErrorResponse();
    private ScreeningResponse screeningResponse = new ScreeningResponse();
    private ScreeningListResponse screeningListResponse = new ScreeningListResponse();

    @GetMapping
    public ResponseEntity<Response<?>> getAllScreenings(@PathVariable int id) {
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie == null) {
            this.errorResponse.set("No movie with that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<Screening> screenings = this.screeningRepository.findByMovie(movie);
        this.screeningListResponse.set(screenings);
        return ResponseEntity.ok(screeningListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createNewScreening(@PathVariable int id, @RequestBody Screening screening) {
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie == null) {
            this.errorResponse.set("No movie with that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        screening.setCreatedAt(LocalDateTime.now());
        screening.setUpdatedAt(LocalDateTime.now());
        screening.setMovie(movie);

        if (screening.getScreenNumber() <= 0 || screening.getCapacity() <= 0 || screening.getStartsAt() == null) {
            this.errorResponse.set("Could not create a new screening, please check all fields are correct");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        this.screeningResponse.set(this.screeningRepository.save(screening));
        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{screening_id}")
    public ResponseEntity<Response<?>> deleteScreening(@PathVariable int id) {
        Screening screeningToDelete = this.screeningRepository.findById(id).orElse(null);
        if (screeningToDelete == null) {
            errorResponse.set("No screening with that id was found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.screeningRepository.delete(screeningToDelete);
        this.screeningResponse.set(screeningToDelete);
        return ResponseEntity.ok(screeningResponse);
    }

}