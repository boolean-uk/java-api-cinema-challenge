package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.CustomResponse;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieRepository repo;

    @Autowired
    private ScreeningRepository screeningRepo;

    @GetMapping
    public ResponseEntity<CustomResponse<List<Movie>>> getAll() {
        return new ResponseEntity<>(
                new CustomResponse<List<Movie>>(repo.findAll()),
                HttpStatus.OK
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomResponse<Movie>> getOne(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(
                new CustomResponse<Movie>(
                        repo.findById(id).orElseThrow(() ->
                            new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "No movies with that id were found"
                            )
                        )
                ),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<CustomResponse<Movie>> createOne(@RequestBody Movie movie) {
        Movie savedMovie = repo.save(movie);

        movie.getScreenings().forEach(s -> {
            s.setMovie(savedMovie);
            screeningRepo.save(s);
        });

        return new ResponseEntity<>(
                new CustomResponse<>(savedMovie),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomResponse<Movie>> updateOne(@PathVariable(name = "id") int id, @RequestBody Movie movie) {
        Movie toUpdate = repo.findById(id)
                            .orElseThrow(() ->
                                    new ResponseStatusException(
                                            HttpStatus.NOT_FOUND,
                                            "No movies with that id were found"
                                    )
                            );

        toUpdate.setTitle(movie.getTitle());
        toUpdate.setDescription(movie.getDescription());
        toUpdate.setRating(movie.getRating());
        toUpdate.setRuntimeMins(movie.getRuntimeMins());

        return new ResponseEntity<>(
                new CustomResponse<>(repo.save(toUpdate)),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CustomResponse<Movie>> deleteOne(@PathVariable(name = "id") int id) {
        Movie deleted = repo.findById(id)
                            .orElseThrow(() ->
                                    new ResponseStatusException(
                                            HttpStatus.NOT_FOUND,
                                            "No movies with that id were found"
                                    )
                            );

        deleted.getScreenings().forEach(s -> screeningRepo.delete(s));
        repo.deleteById(id);

        return new ResponseEntity<>(
                new CustomResponse<>(deleted),
                HttpStatus.OK
        );
    }
}
