package com.booleanuk.api.cinema.screens;



import com.booleanuk.api.cinema.customers.Customer;
import com.booleanuk.api.cinema.movies.MovieRepository;
import com.booleanuk.api.cinema.movies.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("movies")
public class ScreenController {

    @Autowired
    ScreenRepository screenRepository;

    @Autowired
    MovieRepository movieRepository;

    @GetMapping("{id}/screenings")
    public ResponseEntity<List<Screen>> getAllScreens(@PathVariable(name = "id") int id) {
        Movie movie = this.movieRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such movie!"));

        return ResponseEntity.ok(movie.getScreens());
    }

    @PostMapping("{id}/screenings")
    public ResponseEntity<Screen> createScreen(@PathVariable(name = "id") int id, @RequestBody Screen screen) {
        Movie movie = this.movieRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such movie!"));
        screen.setMovie(movie);
        screen.setCreated_at(String.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(this.screenRepository.save(screen), HttpStatus.CREATED);
    }

/*  NOT DONE!! CONTAINS ERRORS!
    @PutMapping("/{id}")
    public ResponseEntity<Screen> updateScreen(@PathVariable(name = "id") int id, @RequestBody Screen screen) {
        Screen screenToUpdate = this.screenRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such screen."));

        Movie movie = this.movieRepository.findById(screen.getMovie().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such movie"));

        screenToUpdate.setMovie(movie);
        screenToUpdate.setScreen_number(screen.getScreen_number());
        screenToUpdate.setStarts_at(screen.getStarts_at());
        screenToUpdate.setCapacity(screen.getCapacity());
        screenToUpdate.setUpdated_at(LocalDateTime.now());
        return new ResponseEntity<>(this.screenRepository.save(screenToUpdate), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Screen> deleteScreen(@PathVariable(name = "id") int id) {
        Screen toDelete = this.screenRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such customer found"));
        this.screenRepository.delete(toDelete);
        return ResponseEntity.ok(toDelete);

    }

 */
}
