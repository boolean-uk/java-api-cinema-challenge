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

        if (movie.getTitle() == null || movie.getRating() == 0 || movie.getDescription() == null || movie.getRuntimeMins() == 0) {
            CustomResponse errResponse = new CustomResponse("Error", new Error("Check if all fields are correct!"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
        }

        Movie movieToUpdate = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        movieToUpdate.setCreatedAt(movie.getCreatedAt());
        movieToUpdate.setUpdatedAt(movie.getUpdatedAt());

        CustomResponse res = new CustomResponse("success", this.movieRepository.save(movieToUpdate));

        return ResponseEntity.ok(res);
    }

}
