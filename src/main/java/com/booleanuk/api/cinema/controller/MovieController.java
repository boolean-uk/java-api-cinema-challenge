package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.response.CustomResponse;
import com.booleanuk.api.cinema.response.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@RequestMapping("movies")
public class MovieController {


    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<CustomResponse> getAllMovies() {

        if (movieRepository.count() < 1) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("No data found"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }


        CustomResponse response = new CustomResponse("success", this.movieRepository.findAll());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getMovieByID(@PathVariable int id) {
        if (!movieRepository.existsById(id)) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Id is not in database!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }
        Movie movie = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        CustomResponse res = new CustomResponse("Success", movie);
        return ResponseEntity.ok(res);
    }


    @PostMapping
    public ResponseEntity<CustomResponse> createMovie(@RequestBody Movie movie) {

        if (movie.getTitle() == null || movie.getRating() == 0 || movie.getDescription() == null || movie.getRuntimeMins() == 0) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Check if all fields are correct!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }
        CustomResponse res = new CustomResponse("success", this.movieRepository.save(movie));

        return ResponseEntity.ok(res);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteMovieByID(@PathVariable int id) {

        if (!movieRepository.existsById(id)) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Id is not in database!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }

        Movie movie = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        this.movieRepository.delete(movie);
        movie.setScreenings(new ArrayList<Screening>());

        CustomResponse res = new CustomResponse("success", movie);
        return ResponseEntity.ok(res);


    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateMovie(@PathVariable int id, @RequestBody Movie movie) {

//        if (movie.getTitle() == null || movie.getRating() == 0 || movie.getDescription() == null || movie.getRuntimeMins() == 0) {
//            CustomResponse errResponse = new CustomResponse("Error", new Error("Check if all fields are correct!"));
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
//        }

        Movie prevMovie = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found!"));
        Movie movieToUpdate = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));

        if (movie.getTitle() == null) {
            movieToUpdate.setTitle(prevMovie.getTitle());
        }
        else {
            movieToUpdate.setTitle(movie.getTitle());
        }
        if (movie.getRating() == 0) {
            movieToUpdate.setRating(prevMovie.getRating());
        }
        else {
            movieToUpdate.setRating(movie.getRating());
        }
        if (movie.getDescription() == null) {
            movieToUpdate.setDescription(prevMovie.getDescription());
        }
        else {
            movieToUpdate.setDescription(movie.getDescription());
        }
        if (movie.getRuntimeMins() == 0) {
            movieToUpdate.setRuntimeMins(prevMovie.getRuntimeMins());
        }
        else {
            movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        }
        if (movie.getCreatedAt() == null) {
            movieToUpdate.setCreatedAt(prevMovie.getCreatedAt());
        }
        else {
            movieToUpdate.setCreatedAt(movie.getCreatedAt());
        }
        movieToUpdate.setUpdatedAt(movie.getUpdatedAt());
        if (movie.getScreenings() == null) {
            movieToUpdate.setScreenings(prevMovie.getScreenings());
        }
        else {
            movieToUpdate.setScreenings(movie.getScreenings());
        }

        CustomResponse res = new CustomResponse("success", this.movieRepository.save(movieToUpdate));

        return ResponseEntity.ok(res);
    }

}
