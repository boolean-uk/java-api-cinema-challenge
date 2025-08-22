package com.booleanuk.api.cinema.movies;

import com.booleanuk.api.cinema.responses.*;
import com.booleanuk.api.cinema.screenings.Screening;
import com.booleanuk.api.cinema.screenings.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("movies")

public class MovieController {

    @Autowired
    private final MovieRepository repository;
    @Autowired
    private final ScreeningRepository screeningRepository;

    public MovieController(MovieRepository repository, ScreeningRepository screeningRepository) {
        this.repository = repository;
        this.screeningRepository = screeningRepository;
    }

    @GetMapping
    public ResponseEntity<MovieListResponse> getAll() {
        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.set(this.repository.findAll());
        return ResponseEntity.ok(movieListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createAMovie(@RequestBody Movie movie) {
        MovieResponse movieResponse = new MovieResponse();
        ScreeningResponse screeningResponse = new ScreeningResponse();
        try {
            movieResponse.set(this.repository.save(movie));
            for(Screening screening : movie.getScreenings()){
                screening = new Screening(movie, screening.getScreenNumber(), screening.getStartsAt(), screening.getCapacity());
                screeningResponse.set(this.screeningRepository.save(screening));
            }

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request - cannot create movie and/or screening(s)");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateAMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = null;
        try {
            movieToUpdate = this.repository.findById(id).orElse(null);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request - could not successfully find movie");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        if (movieToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Artist not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        movieToUpdate.setUpdatedAt(LocalDate.now());
        movieToUpdate = this.repository.save(movieToUpdate);
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movieToUpdate);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteAMovie(@PathVariable int id) {
        Movie movieToDelete = this.repository.findById(id).orElse(null);
        if (movieToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Movie not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.repository.delete(movieToDelete);
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movieToDelete);
        return ResponseEntity.ok(movieResponse);
    }
}
