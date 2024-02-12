package com.booleanuk.api.cinema.movie;

import com.booleanuk.api.cinema.screening.Screening;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository repo;

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie){
        movie.setCreatedAt(LocalDateTime.now());
        movie.setUpdatedAt(LocalDateTime.now());

        for (Screening screening : movie.getScreenings()){
            screening.setMovie(movie);
            screening.setCreatedAt(LocalDateTime.now());
            screening.setUpdatedAt(LocalDateTime.now());
        }

        return new ResponseEntity<Movie>(repo.save(movie), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Movie> getAll(){
        return repo.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Movie> getOne(@PathVariable int id){
        return ResponseEntity.ok(getById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Movie> update(@PathVariable int id, @RequestBody Movie movie){
        Movie toUpdate = getById(id);

        Optional.ofNullable(movie.getTitle())
                .ifPresent(title -> toUpdate.setTitle(title));
        Optional.ofNullable(movie.getRating())
                .ifPresent(rating -> toUpdate.setRating(rating));
        Optional.ofNullable(movie.getDescription())
                .ifPresent(description -> toUpdate.setDescription(description));
        Optional.of(movie.getRuntimeMins())
                .ifPresent(runtimeMins -> toUpdate.setRuntimeMins(runtimeMins));

        toUpdate.setUpdatedAt(LocalDateTime.now());

        return new ResponseEntity<Movie>(repo.save(toUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Movie> delete(@PathVariable int id){
        Movie toDelete = getById(id);
        repo.delete(toDelete);
        toDelete.setScreenings(new ArrayList<Screening>());

        return ResponseEntity.ok(toDelete);
    }

    private Movie getById(int id){
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
