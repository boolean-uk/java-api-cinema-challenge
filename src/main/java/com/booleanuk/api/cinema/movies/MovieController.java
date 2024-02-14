package com.booleanuk.api.cinema.movies;

import com.booleanuk.api.cinema.screenings.Screening;
import com.booleanuk.api.cinema.screenings.ScreeningRepository;
import com.booleanuk.api.helpers.ResponseHandler;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
    private MovieRepository movieRepository;


    @Autowired
    private ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseHandler.generateResponse(HttpStatus.OK, this.movieRepository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable int id) {
        try {
            return ResponseHandler.generateResponse(HttpStatus.OK, this.movieRepository
                    .findById(id)
                    .orElseThrow(Exception::new));
        } catch (Exception e) {
            return ResponseHandler.generateError(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<Object> addOne(@Valid @RequestBody Movie movie) {
        for (Screening screening : movie.getScreenings()) {
            screening.setMovie(movie);
        }
        Movie savedMovie = this.movieRepository.save(movie);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, savedMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOne(@PathVariable int id, @Valid @RequestBody Movie movie) {
        try {
            Movie movieToUpdate = this.movieRepository
                    .findById(id)
                    .orElseThrow(Exception::new);
            if(movie.getTitle() != null){
                movieToUpdate.setTitle(movie.getTitle());
            }
            if(movie.getRating() != null){
                movieToUpdate.setRating(movie.getRating());
            }
            if(movie.getDescription() != null){
                movieToUpdate.setDescription(movie.getDescription());
            }
            if(movie.getRuntimeMins() != 0){
                movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
            }
            return new ResponseEntity<>(movieToUpdate, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseHandler.generateError(HttpStatus.NOT_FOUND);
        }


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable int id) {
        try {
            Movie movie = this.movieRepository
                    .findById(id)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);
            this.movieRepository.delete(movie);
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseHandler.generateError(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseHandler.generateError(HttpStatus.BAD_REQUEST);
        }


    }
}
