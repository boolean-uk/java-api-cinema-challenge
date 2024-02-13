package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
//@RequestMapping("movies")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;



    @PostMapping("/movies/{id}/screenings")
    public ResponseEntity<ResponseGeneric<?>> createScreening(@PathVariable int id, @RequestBody Screening screening){
       Movie movie = this.getAMovie(id);
        if(screening.getScreenNumber() == null || screening.getCapacity() == null){
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

       if (movie == null){
           ErrorResponse error = new ErrorResponse();
           error.set("not found");
           return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
       }

       screening.setMovie(movie);
        Screening savedScreening = this.screeningRepository.save(screening);
        ScreeningResponse screeningResponse = new ScreeningResponse();
        screeningResponse.set(savedScreening);
       return new ResponseEntity<>( screeningResponse, HttpStatus.CREATED);
    }

    @GetMapping("movies/{id}/screenings")
    public ResponseEntity<ScreeningListResponse> getAllScreening(@PathVariable int id){
        Movie movie = this.getAMovie(id);
        List<Screening> screenings = movie.getScreenings();
        ScreeningListResponse screeningListResponse = new ScreeningListResponse();
        screeningListResponse.set(screenings);
        //screeningListResponse.set(this.screeningRepository.findAll());
        return ResponseEntity.ok(screeningListResponse);
    }

    private Movie getAMovie(int id) {

        return this.movieRepository.findById(id)
                .orElse(null);
    }


}
