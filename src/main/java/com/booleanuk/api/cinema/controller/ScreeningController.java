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

import java.util.List;

@RestController
@RequestMapping("movies")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;
    @GetMapping("/{id}/screenings")
    public List<Screening> getAll(@PathVariable int id) {
        Movie tempMovie = this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies with that ID were found"));

        return tempMovie.getScreenings();
    }

    @PostMapping("/{id}/screenings")
    public ResponseEntity<Screening> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        Movie tempMovie = this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies with that ID were found"));
        screening.setMovie(tempMovie);
        if(containsNull(screening)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create screening, please check all required fields are correct.");
        }

        return new ResponseEntity<>(screeningRepository.save(screening), HttpStatus.CREATED);
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
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the screening, please check all required fields are correct.");
//        }
//        screeningToUpdate.setScreenNumber(screening.getScreenNumber());
//        screeningToUpdate.setStartsAt(screening.getStartsAt());
//        screeningToUpdate.setCapacity(screening.getCapacity());
//        screeningToUpdate.setMovie(screening.getMovie());
//
//        return new ResponseEntity<>(screeningRepository.save(screeningToUpdate), HttpStatus.CREATED);
//    }

    private Screening findScreening(int id) {
        return screeningRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screenings with that id were found"));
    }

    private boolean containsNull(Screening screening) {
        System.out.println(screening.getScreenNumber() + "  " + screening.getStartsAt() + "   " + screening.getCapacity() + screening.getMovie());
        return screening.getScreenNumber() == null || screening.getStartsAt() == null || screening.getCapacity() == null || screening.getMovie() == null;
    }
}


