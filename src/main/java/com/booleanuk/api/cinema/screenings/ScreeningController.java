package com.booleanuk.api.cinema.screenings;

import com.booleanuk.api.cinema.movies.Movie;
import com.booleanuk.api.cinema.movies.MovieRepository;
import com.booleanuk.api.helpers.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;
    @GetMapping
    public ResponseEntity<Object> getAll(@PathVariable int id){
        return ResponseHandler.generateResponse(HttpStatus.OK, this.screeningRepository.findByMovieId(id));
    }

    @PostMapping
    public ResponseEntity<Object> addOne(@PathVariable int id, @Valid @RequestBody Screening screening){
        try{
            Movie movie = this.movieRepository
                    .findById(id)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);

            screening.setMovie(movie);
            return ResponseHandler.generateResponse(HttpStatus.CREATED, this.screeningRepository.save(screening));
        }
        catch (ChangeSetPersister.NotFoundException e){
            return ResponseHandler.generateError(HttpStatus.BAD_REQUEST);
        }

    }
}
