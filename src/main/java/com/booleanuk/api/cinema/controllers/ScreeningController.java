package com.booleanuk.api.cinema.controllers;


import com.booleanuk.api.cinema.dtos.ResponseDTO;
import com.booleanuk.api.cinema.exceptions.BadRequestException;
import com.booleanuk.api.cinema.exceptions.NotFoundException;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<?> getAll(@PathVariable int id) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No movie with that id: " + id + " found")
        );
        return ResponseEntity.ok(screeningRepository.findByMovie(movie));
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Screening screening, @PathVariable int id) {

       Movie movie = this.movieRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No movie with that id: " + id + " found")
        );

        try{
            screening.setMovie(movie);
            this.screeningRepository.save(screening);
            ResponseDTO<Screening> response = new ResponseDTO<>(
                    "success",
                    screening);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }

    }

}
