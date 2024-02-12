package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.exceptions.CustomDataNotFoundException;
import com.booleanuk.api.cinema.exceptions.CustomParameterConstraintException;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.util.DateCreater;
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

    @PostMapping
    public ResponseEntity<Screening> createScreening(@PathVariable (name = "id") int id, @RequestBody Screening screening) {
        Movie movie = this.getAMovie(id);
        Screening newScreening = new Screening(screening.getScreenNumber(), screening.getStartsAt(), screening.getCapacity(), DateCreater.getCurrentDate(), DateCreater.getCurrentDate(), movie);
        areScreeningValid(newScreening);
        movie.setUpdatedAt(DateCreater.getCurrentDate());
        movie.getScreenings().add(screening);
        return new ResponseEntity<>(this.screeningRepository.save(newScreening), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Screening> getScreenings(@PathVariable (name = "id") int id) {
        Movie movie = this.getAMovie(id);
        return movie.getScreenings();
    }



    private Screening getAScreening(int id) {
        return this.screeningRepository.findById(id).orElseThrow(() -> new CustomDataNotFoundException("No screening with that id were find."));
    }

    private Movie getAMovie(int id) {
        return this.movieRepository.findById(id).orElseThrow(() -> new CustomParameterConstraintException("No movie with that id were find."));
    }

    private void areScreeningValid(Screening screening) {
        if(screening.getCreatedAt() == null || screening.getUpdatedAt() == null || screening.getStartsAt() == null) {
            throw new CustomParameterConstraintException("Could not create a screening for the specified movie, please check all fields are correct");
        }

    }

}
