package com.booleanuk.api.cinema.screening;

import com.booleanuk.api.cinema.movie.Movie;
import com.booleanuk.api.cinema.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

public class ScreeningController {
    @Autowired
    private ScreeningRepository repo;

    @Autowired
    private MovieRepository movies;

    @PostMapping
    public ResponseEntity<Screening> create(@RequestBody Screening screening){
        Screening created = repo.save(screening);

        Movie tempMovie = movies
                .findById(screening.getMovie().getId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with this ID."));

        screening.setMovie(tempMovie);
        screening.setCreatedAt(LocalDateTime.now());
        screening.setUpdatedAt(LocalDateTime.now());

        return new ResponseEntity<Screening>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Screening> getAll(){
        return repo.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Screening> getOne(@PathVariable int id){
        return ResponseEntity.ok(getById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Screening> update(@PathVariable int id, @RequestBody Screening screening){
        Screening toUpdate = getById(id);

        Movie tempMovie = movies
                .findById(screening.getMovie().getId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with this ID."));

        toUpdate.setMovie(tempMovie);
        toUpdate.setScreenNumber(screening.getScreenNumber());
        toUpdate.setStartsAt(screening.getStartsAt());
        toUpdate.setCapacity(toUpdate.getCapacity());
        toUpdate.setUpdatedAt(LocalDateTime.now());

        return new ResponseEntity<Screening>(repo.save(toUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Screening> delete(@PathVariable int id){
        Screening toDelete = getById(id);
        repo.delete(toDelete);

        return ResponseEntity.ok(toDelete);
    }

    private Screening getById(int id){
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
