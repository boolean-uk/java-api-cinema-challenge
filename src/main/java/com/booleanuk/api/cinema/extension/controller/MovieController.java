package com.booleanuk.api.cinema.extension.controller;

import com.booleanuk.api.cinema.extension.model.Movie;
import com.booleanuk.api.cinema.extension.model.MovieDTO;
import com.booleanuk.api.cinema.extension.model.MovieDTOMapper;
import com.booleanuk.api.cinema.extension.model.Screening;
import com.booleanuk.api.cinema.extension.repository.MovieRepository;
import com.booleanuk.api.cinema.extension.repository.ScreeningRepository;
import com.booleanuk.api.cinema.extension.response.CustomResponse;
import com.booleanuk.api.cinema.extension.response.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    private final MovieDTOMapper movieDTOMapper;

    public MovieController(MovieDTOMapper movieDTOMapper) {
        this.movieDTOMapper = movieDTOMapper;
    }

    @GetMapping
    public ResponseEntity<CustomResponse> getAllMovies() {
        CustomResponse customResponse = new CustomResponse("success", movieRepository.findAll()
                .stream()
                .map(movieDTOMapper));
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomResponse> createMovie(@RequestBody Movie movie) {
        checkIfValidMovie(movie);

        movieRepository.save(movie);

        for(Screening screening : movie.getScreenings()) {
            screening.setMovie(movie);
            screeningRepository.save(screening);
        }

        CustomResponse customResponse = new CustomResponse("success", movieRepository.findById(movie.getId()).stream()
                .map(movieDTOMapper));
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomResponse> updateMovie(@PathVariable("id") Long id, @RequestBody Movie movie) {
        if(!movieRepository.existsById(id)) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }

        Movie movieToUpdate = movieRepository
                .findById((long) id).get();
        movieToUpdate.setTitle(movie.getTitle() != null ? movie.getTitle() : movieToUpdate.getTitle());
        movieToUpdate.setRating(movie.getRating() != null ? movie.getRating() : movieToUpdate.getRating());
        movieToUpdate.setDescription(movie.getDescription() != null ? movie.getDescription() : movieToUpdate.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins() != null ? movie.getRuntimeMins() : movieToUpdate.getRuntimeMins());
        movieRepository.save(movieToUpdate);

        CustomResponse customResponse = new CustomResponse("success", movieRepository.findById(movieToUpdate.getId()).stream()
                .map(movieDTOMapper));
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CustomResponse> deleteMovie(@PathVariable("id") Long id) {
        if(!movieRepository.existsById(id)) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }

        Movie movieToDelete = movieRepository
                .findById((long) id).get();
        movieRepository.delete(movieToDelete);
        movieToDelete.setScreenings(new ArrayList<>(movieToDelete.getScreenings()));

        CustomResponse customResponse = new CustomResponse("success", movieToDelete);
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    public ResponseEntity<CustomResponse> checkIfValidMovie(@RequestBody Movie movie) {
        if (movie.getTitle() == null || movie.getTitle().isEmpty()) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("Title is required")), HttpStatus.BAD_REQUEST);
        } else if (movie.getRating() == null || movie.getRating().isEmpty()) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("Rating is required")), HttpStatus.BAD_REQUEST);
        } else if (movie.getDescription() == null || movie.getDescription().isEmpty()) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("Description is required")), HttpStatus.BAD_REQUEST);
        } else if (movie.getRuntimeMins() == null || movie.getRuntimeMins() == 0){
            return new ResponseEntity<>(new CustomResponse("error", new Error("Runtime is required")), HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}