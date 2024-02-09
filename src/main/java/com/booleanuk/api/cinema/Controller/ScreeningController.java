package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.Model.Customer;
import com.booleanuk.api.cinema.Model.Movie;
import com.booleanuk.api.cinema.Model.Screening;
import com.booleanuk.api.cinema.Model.Ticket;
import com.booleanuk.api.cinema.Repository.MovieRepository;
import com.booleanuk.api.cinema.Repository.ScreeningRepository;
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
    public List<Screening> getAll() {
        return this.repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Screening> create(
            @PathVariable (name= "id") Integer movieId,
            @RequestBody Screening request) {

        validate(request);

        Movie movie = this.movieRepository.findById(movieId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        Screening screening = new Screening(movie, request.getScreenNumber(), request.getStartsAt(), request.getCapacity());
        return new ResponseEntity<Screening>(this.repository.save(screening), HttpStatus.CREATED);
    }

    public void validate(Screening screening) {
        if (screening.getScreenNumber() == null || screening.getStartsAt() == null || screening.getCapacity() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Could not create/update Ticket, please check all required fields are correct.");
        }
    }
}
