package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.BadRequestResponse;
import com.booleanuk.api.cinema.response.NotFoundResponse;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("movies")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;
    @GetMapping("/{id}/screenings")
    public ResponseEntity<Response> getAll(@PathVariable int id) {
        Movie tempMovie = this.movieRepository.findById(id).orElse(null);
        if(tempMovie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NotFoundResponse());
        }

        return ResponseEntity.status(HttpStatus.OK).body(new Response(tempMovie.getScreenings(), "success"));
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
        return ResponseEntity.status(HttpStatus.OK).body(new Response(screeningRepository.save(screening), "success"));
    }


//    @GetMapping("/{id}")
//    public ResponseEntity<Screening> getScreeningById(@PathVariable int id) {
//        Screening screening = findScreening(id);
//        return ResponseEntity.ok(screening);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Screening> deleteScreening(@PathVariable int id) {
//        Screening screeningToDelete = findScreening(id);
//        screeningRepository.delete(screeningToDelete);
//        return ResponseEntity.ok(screeningToDelete);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Screening> updateScreening(@PathVariable int id, @RequestBody Screening screening) {
//        Screening screeningToUpdate = findScreening(id);
//
//        if(containsNull(screening)) {
//            Ticket new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the screening, please check all required fields are correct.");
//        }
//        screeningToUpdate.setScreenNumber(screening.getScreenNumber());
//        screeningToUpdate.setStartsAt(screening.getStartsAt());
//        screeningToUpdate.setCapacity(screening.getCapacity());
//        screeningToUpdate.setMovie(screening.getMovie());
//
//        return new ResponseEntity<>(screeningRepository.save(screeningToUpdate), HttpStatus.CREATED);
//    }

    private Screening findScreening(int id) {
        return screeningRepository.findById(id).orElse(null);
    }

    private boolean containsNull(Screening screening) {
        return screening.getScreenNumber() == null || screening.getStartsAt() == null || screening.getCapacity() == null || screening.getMovie() == null;
    }
}


