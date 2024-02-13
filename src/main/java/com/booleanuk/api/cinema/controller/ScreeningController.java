package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.helpers.CustomResponse;
import com.booleanuk.api.cinema.helpers.ErrorResponse;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class ScreeningController {

    @Autowired
    ScreeningRepository screeningRepository;
    @Autowired
    MovieRepository movieRepository;

    @GetMapping("/{id}/screenings")
    public ResponseEntity<CustomResponse> getScreeningsByMovieId(@PathVariable int id){
        if(!movieRepository.existsById(id)){
            return new ResponseEntity<>(new CustomResponse("error", new ErrorResponse("No movie with that id were found")), HttpStatus.NOT_FOUND);
        }
        Movie movie = movieRepository
                .findById(id).get();
        List<Screening> listScreens = new ArrayList<>(movie.getScreenings());
        return new ResponseEntity<>(new CustomResponse("success", listScreens), HttpStatus.OK);
    }
    @PostMapping("/{id}/screenings")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomResponse> createScreening(@RequestBody Screening screening, @PathVariable int id) {
        if(!movieRepository.existsById(id)){
            return new ResponseEntity<>(new CustomResponse("error", new ErrorResponse("No movie with that id were found")), HttpStatus.NOT_FOUND);
        }
        if(screening.getScreenNumber() == 0 || screening.getCapacity() == 0 || screening.getStartsAt() == null){
            return new ResponseEntity<>(new CustomResponse("error", new ErrorResponse("Could not update movie, please check all required fields are correct")), HttpStatus.BAD_REQUEST);
        }
        screening.setMovie(movieRepository.findById(id).get());
        screeningRepository.save(screening);
        return new ResponseEntity<>(new CustomResponse("success", screening), HttpStatus.CREATED);
    }



}
