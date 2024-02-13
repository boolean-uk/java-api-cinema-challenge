package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.Model.Customer;
import com.booleanuk.api.cinema.Model.Movie;
import com.booleanuk.api.cinema.Model.Screening;
import com.booleanuk.api.cinema.Model.Ticket;
import com.booleanuk.api.cinema.Repository.MovieRepository;
import com.booleanuk.api.cinema.Repository.ScreeningRepository;
import com.booleanuk.api.cinema.Response.Error;
import com.booleanuk.api.cinema.Response.Response;
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
    private ScreeningRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<Screening> all = this.repository.findAll();
        return new ResponseEntity<Object>(new Response("success", all), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> create(
            @PathVariable (name= "id") Integer movieId,
            @RequestBody Screening screening) {
        if (screening.getScreenNumber() == null || screening.getStartsAt() == null || screening.getCapacity() == null) {
            return new ResponseEntity<Object>(new Response("error", new Error("bad request")), HttpStatus.BAD_REQUEST);
        }
        if (this.movieRepository.findById(movieId).isEmpty()) {
            return new ResponseEntity<Object>(new Response("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }
        Movie movie = this.movieRepository.findById(movieId).get();

        screening.setTime();
        screening.setMovie(movie);
        screening = this.repository.save(screening);

        return new ResponseEntity<Object>(new Response("success", screening), HttpStatus.CREATED);
    }
}
