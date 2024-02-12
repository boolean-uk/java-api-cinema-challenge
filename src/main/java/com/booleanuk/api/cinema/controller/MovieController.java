package com.booleanuk.api.cinema.controller;


import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.booleanuk.api.cinema.helpers.CustomResponse;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<CustomResponse> getAll(){
        return new ResponseEntity<>(new CustomResponse("success", movieRepository.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> get(@PathVariable int id){
        if(!movieRepository.existsById(id)){
            return new ResponseEntity<>(new CustomResponse("error", "No movie with that id were found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new CustomResponse("success", movieRepository.findById(id).get()), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomResponse> create(@RequestBody Movie movie){
        if(movie.getTitle() == null || movie.getDescription() == null || movie.getRating() == null){
            return new ResponseEntity<>(new CustomResponse("error", "Could not update movie, please check all required fields are correct"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new CustomResponse("success", movieRepository.save(movie)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomResponse> update(@RequestBody Movie movie, @PathVariable int id){
        if(!movieRepository.existsById(id)){
            return new ResponseEntity<>(new CustomResponse("error", "No movie with that id were found"), HttpStatus.NOT_FOUND);
        }
        Movie existingMovie = movieRepository
                .findById(id).get();
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setDescription(movie.getDescription());
        existingMovie.setRating(movie.getRating());
        existingMovie.setRuntimeMins(movie.getRuntimeMins());
        existingMovie.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));

        if(existingMovie.getTitle() == null || existingMovie.getRating() == null || existingMovie.getRuntimeMins() == 0 || existingMovie.getDescription() == null){
            return new ResponseEntity<>(new CustomResponse("error", "Could not update movie, please check all required fields are correct"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new CustomResponse("success", movieRepository.save(existingMovie)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> delete(@PathVariable int id){
        if(!movieRepository.existsById(id)){
            return new ResponseEntity<>(new CustomResponse("error", "No movie with that id were found"), HttpStatus.NOT_FOUND);
        }
        Movie movie = movieRepository
                .findById(id).get();
        movieRepository.delete(movie);
        return new ResponseEntity<>(new CustomResponse("success", movie), HttpStatus.CREATED);
    }
}
