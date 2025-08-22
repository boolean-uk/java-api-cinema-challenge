package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.payload.request.CreateMovieRequest;
import com.booleanuk.api.cinema.payload.response.ErrorResponse;
import com.booleanuk.api.cinema.payload.response.MovieListResponse;
import com.booleanuk.api.cinema.payload.response.MovieResponse;
import com.booleanuk.api.cinema.payload.response.Response;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    MovieRepository repo;

    @Autowired
    ScreeningRepository screeningRepo;

    @Autowired
    TicketRepository ticketRepo;

    @GetMapping
    public ResponseEntity<MovieListResponse> getAll(){
        MovieListResponse movieList = new MovieListResponse();
        movieList.set(this.repo.findAll());
        return ResponseEntity.ok(movieList);
    }

    @PostMapping
    public ResponseEntity<Response<?>> addOne(@Valid @RequestBody CreateMovieRequest req){

        Movie movie = new Movie(req.getTitle(), req.getRating(), req.getDescription(), req.getRuntimeMins());
        movie.setCreated(OffsetDateTime.now());
        movie.setUpdatedAt(OffsetDateTime.now());

        Integer createdId = repo.save(movie).getId();

        List<Screening> screenings = req.getScreenings();
        if(screenings != null){
            for(Screening screening : screenings){
               screening.setMovie(movie);
                screening.setCreated(OffsetDateTime.now());
                screening.setUpdatedAt(OffsetDateTime.now());

               this.screeningRepo.save(screening);
            }
        }

        MovieResponse resp = new MovieResponse();
        Movie newMovie = repo.findById(createdId).orElse(null);

        if(newMovie == null){
            ErrorResponse error = new ErrorResponse();
            error.set("Not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        resp.set(newMovie);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> editOne(@PathVariable Integer id, @Valid @RequestBody Movie movie){
        Movie toBeEdited = this.repo.findById(id).orElse(null);

        if(toBeEdited == null){
            ErrorResponse error = new ErrorResponse();
            error.set("Not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        toBeEdited.setTitle(movie.getTitle());
        toBeEdited.setRating(movie.getRating());
        toBeEdited.setDescription(movie.getDescription());
        toBeEdited.setRuntimeMins(movie.getRuntimeMins());

        toBeEdited.setUpdatedAt(OffsetDateTime.now());

        MovieResponse resp = new MovieResponse();
        resp.set(this.repo.save(toBeEdited));
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteOne(@PathVariable Integer id){
        Movie toBeDeleted = this.repo.findById(id).orElse(null);

        if(toBeDeleted == null){
            ErrorResponse error = new ErrorResponse();
            error.set("Not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        for(Screening curr : toBeDeleted.getScreenings()){
            this.ticketRepo.deleteAll(curr.getTickets());
            this.screeningRepo.delete(curr);
        }

        this.repo.delete(toBeDeleted);
        MovieResponse resp = new MovieResponse();
        resp.set(toBeDeleted);
        return ResponseEntity.ok(resp);
    }
}
