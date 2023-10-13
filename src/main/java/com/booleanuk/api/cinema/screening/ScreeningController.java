package com.booleanuk.api.cinema.screening;

import com.booleanuk.api.cinema.movie.Movie;
import com.booleanuk.api.cinema.movie.MovieRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.ScreeningListResponse;
import com.booleanuk.api.cinema.response.ScreeningResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAllScreenings(@PathVariable int id) {
        ScreeningListResponse screeningListResponse = new ScreeningListResponse();

        Movie movie = this.movieRepository.findById(id).orElse(null);

        if (movie == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Movie not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        screeningListResponse.set(this.screeningRepository.findByMovieId(id));
        return ResponseEntity.ok(screeningListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        ScreeningResponse screeningResponse = new ScreeningResponse();

        if (screening.getScreenNumber() == 0 || screening.getCapacity() == 0 || screening.getStartsAt() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Some fields are invalid");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

        } else {
            Screening alr = this.screeningRepository.findByScreenNumber(screening.getScreenNumber());
            if (alr != null) {
                ErrorResponse error = new ErrorResponse();
                error.set("Screening already exists");
                return new ResponseEntity<>(error, HttpStatus.CONFLICT);
            } else {
                Movie movie = this.movieRepository.findById(id).orElse(null);

                if (movie == null) {
                    ErrorResponse error = new ErrorResponse();
                    error.set("Movie not found");
                    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
                }

                Date today = new Date();
                screening.setCreatedAt(today);
                screening.setUpdatedAt(today);
                screening.setMovie(movie);

                screeningResponse.set(this.screeningRepository.save(screening));
            }
            return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
        }
    }
}
