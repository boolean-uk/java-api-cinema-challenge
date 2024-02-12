package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.BadRequestResponse;
import com.booleanuk.api.cinema.response.NotFoundResponse;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movies")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;
    @GetMapping("/{id}/screenings")
    public ResponseEntity<Response> getAllScreenings(@PathVariable int id) {
        Movie tempMovie = this.movieRepository.findById(id).orElse(null);
        if(tempMovie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NotFoundResponse());
        }

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(tempMovie.getScreenings()));
    }

    @PostMapping("/{id}/screenings")
    public ResponseEntity<Response> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        Movie tempMovie = this.movieRepository.findById(id).orElse(null);
        if(tempMovie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NotFoundResponse());
        }
        screening.setMovie(tempMovie);
        if(containsNull(screening)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BadRequestResponse());
        }
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(screeningRepository.save(screening)));
    }

    private Screening findScreening(int id) {
        return screeningRepository.findById(id).orElse(null);
    }

    private boolean containsNull(Screening screening) {
        return screening.getScreenNumber() == null || screening.getStartsAt() == null || screening.getCapacity() == null || screening.getMovie() == null;
    }
}


