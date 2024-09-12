package com.booleanuk.api.cinema.controller;


import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.MovieListResponse;
import com.booleanuk.api.cinema.response.MovieResponse;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private ScreeningRepository screeningRepository;

    public MovieController(MovieRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.set(this.repository.findAll());
        return ResponseEntity.ok(movieListResponse);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Object> createMovie(@RequestBody Movie movie) {
        Movie savedMovie = this.repository.save(movie);

        if (movie.getScreenings()!=null) {
            for (Screening screening : movie.getScreenings()){
                screening.setMovie(savedMovie);
                this.screeningRepository.save(screening);
            }
        }

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(savedMovie);
        return ResponseEntity.ok(movieResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateMovie(@PathVariable int id,
                                           @RequestBody Movie movie) {
        Movie movieToUpdate = this.repository.findById(id).orElse(null);
        if (movieToUpdate == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRunTimeMins(movie.getRunTimeMins());
        movieToUpdate.setUpdatedAt(OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS));

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(this.repository.save(movieToUpdate));
        return ResponseEntity.ok(movieResponse);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.repository.findById(id).orElse(null);
        if (movieToDelete == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.repository.delete(movieToDelete);
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movieToDelete);
        return ResponseEntity.ok(movieResponse);
    }
}

