package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.Model.Movie;
import com.booleanuk.api.cinema.Model.Screening;
import com.booleanuk.api.cinema.Repository.MovieRepository;
import com.booleanuk.api.cinema.Repository.ScreeningRepository;
import com.booleanuk.api.cinema.Response.Error;
import com.booleanuk.api.cinema.Response.Response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<Movie> all = this.repository.findAll();
        return new ResponseEntity<Object>(new Response("success", all), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        if (this.repository.findById(id).isEmpty()) {
            return new ResponseEntity<Object>(new Response("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }
        Movie movie = this.repository.findById(id).get();
        return new ResponseEntity<Object>(new Response("success", movie), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Movie request) {

        if (request.getTitle() == null ||
                request.getRating() == null ||
                request.getDescription() == null ||
                request.getRuntimeMins() == null) {
            return new ResponseEntity<Object>(new Response("error", new Error("bad request")), HttpStatus.BAD_REQUEST);
        }

        Movie movie = new Movie(request.getTitle(), request.getRating(), request.getDescription(), request.getRuntimeMins(), new ArrayList<>());
        movie.setTime();

        this.repository.save(movie);


        if (request.getScreenings() != null){

            for (Screening screening : request.getScreenings()) {
                screening.setMovie(movie);
                screening.setTime();
            }
            List<Screening> screenings = this.screeningRepository.saveAll(request.getScreenings());
        }

        movie.setScreenings(request.getScreenings());

        return new ResponseEntity<Object>(new Response("success", movie), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody Movie request) {
        if (this.repository.findById(id).isEmpty()) {
            return new ResponseEntity<Object>(new Response("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }
        if (request.getTitle() == null ||
                request.getRating() == null ||
                request.getDescription() == null ||
                request.getRuntimeMins() == null) {
            return new ResponseEntity<Object>(new Response("error", new Error("bad request")), HttpStatus.BAD_REQUEST);
        }

        Movie movie = this.repository.findById(id).get();

        movie.setTitle(request.getTitle());
        movie.setRating(request.getRating());
        movie.setDescription(request.getDescription());
        movie.setRuntimeMins(request.getRuntimeMins());
        movie.updateUpdatedAt();
        movie = this.repository.save(movie);
        return new ResponseEntity<Object>(new Response("success", movie), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        if (this.repository.findById(id).isEmpty()) {
            return new ResponseEntity<Object>(new Response("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }
        Movie movie = this.repository.findById(id).get();
        this.repository.delete(movie);
        return new ResponseEntity<Object>(new Response("success", movie), HttpStatus.OK);
    }
}
