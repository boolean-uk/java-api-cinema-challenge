package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.Model.Movie;
import com.booleanuk.api.cinema.Model.Screening;
import com.booleanuk.api.cinema.Repository.MovieRepository;
import com.booleanuk.api.cinema.Repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("screenings")
public class ScreeningController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @PostMapping("{id}")
    public ResponseEntity<Screening> createScreening(@RequestBody Screening screening, @PathVariable("id") Integer id){


        try {


            Movie movie = this.movieRepository.findById(
                    id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id exists")
            );



            screening.setMovie(movie);
            return new ResponseEntity<Screening>(this.screeningRepository.save(screening),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create screening for the specified " +
                    "movie, please check all required fields are correct.");
        }


    }

    @GetMapping("{id}")
    public ResponseEntity<List<Screening>> getAll(@PathVariable("id") Integer id) {
        List<Screening> allMovs=new ArrayList<>();


        Movie movie = this.movieRepository.findById(
                id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id exists")
        );


        return new ResponseEntity<List<Screening>>(movie.getScreenings(), HttpStatus.FOUND);

    }





}
